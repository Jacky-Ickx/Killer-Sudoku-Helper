import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { SudokuService } from '../sudoku/sudoku.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { KillerSudoku } from 'src/app/models/killer-sudoku.model';
import { IMessage } from '@stomp/rx-stomp';
import { RxStompService } from 'src/app/rx-stomp.service';

@Injectable({
	providedIn: 'root'
})
export class SudokuApiService {
	private basePath = `${environment.protocol}://${environment.backend_url}`;

	constructor(
		private sudoku: SudokuService, 
		private httpClient: HttpClient, 
		private router: Router,
		private rxStompService: RxStompService
	) { }

	sendAsStartingGrid(): Observable<string> {
		const startingGrid: number[][] = []
		this.sudoku.grid.forEach(row => {
			startingGrid.push(
				row.map(cell => cell.values.length > 0 ? cell.values[0] : 0)
			);
		});
		this.sudoku.cages.forEach(cage => {
			cage.cells.forEach(cell => {
				delete cell['adjacency']
			})
		})

		return this.httpClient.post(
			`${this.basePath}/games`,
			{
				grid: startingGrid,
				cages: this.sudoku.cages
			},
			{ responseType: 'text' }
		);
	}

	applyCurrentState(id: string) {
		console.debug(`requesting state for ${id}`)

		this.httpClient.get<KillerSudoku>(`${this.basePath}/games/${id}`).pipe(
			catchError(error => {
				let errorMsg: string;

				if (error.error instanceof ErrorEvent) {
					errorMsg = `${error.error.message}`;
				}
				else {
					errorMsg = `${error.message}`;
				}

				console.error(errorMsg);

				return of(null);
			})
		).subscribe((sudoku: KillerSudoku | null) => {
			if (sudoku === null) {
				this.router.navigate(['404']).then(nav => {
					console.debug(nav);
				}).catch(err => {
					console.error(err);
				});
			}

			this.sudoku.applyState(sudoku!);

			this.joinSession(id);
		});;
	}

	joinSession(id: string) {
		this.sudoku.sendActions = true;
		this.sudoku.actions.subscribe((action) => {
			console.log(action);

			this.rxStompService.publish({
				destination: `/session/handler/${id}/action`,
				body: JSON.stringify(action)
			});
		});

		this.rxStompService.watch(`/session/broker/${id}`).subscribe(message => this.handlePlainMessage(message));
		this.rxStompService.watch(`/session/broker/${id}/actions`).subscribe(message => this.handleJsonMessage(message));
		this.rxStompService.watch(`/session/broker/${id}/error`).subscribe(message => this.handleJsonMessage(message, true));

		this.rxStompService.publish({
			destination: `/session/handler/${id}/hello`,
			body: 'test message'
		});
	}

	handlePlainMessage(message: IMessage) {
		console.debug(message.body);
	}

	handleJsonMessage(message: IMessage, isError:boolean = false) {
		if(isError) console.error(JSON.parse(message.body));
		else console.debug(JSON.parse(message.body));
	}
}

