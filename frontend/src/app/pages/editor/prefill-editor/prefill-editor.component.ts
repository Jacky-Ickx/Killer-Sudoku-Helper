import { Component } from '@angular/core';
import { SudokuService } from 'src/app/core/services/sudoku/sudoku.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SudokuApiService } from '../../../core/services/sudoku-api/sudoku-api.service';

@Component({
	selector: 'app-prefill-editor',
	templateUrl: './prefill-editor.component.html',
	styleUrls: ['./prefill-editor.component.scss']
})
export class PrefillEditorComponent {
	startingGame: boolean = false;

	constructor(private sudoku: SudokuService, private api: SudokuApiService, private router: Router) {
		this.sudoku.inputMethod = 'prefill';
		this.sudoku.removeHighlights();
		this.sudoku.enableInput();
	}

	onFinishClick() {
		this.startingGame = true;
		console.debug(`sending starting grid`)
		this.api.sendAsStartingGrid().pipe(
			catchError(error => {
				let errorMsg: string;

				if (error.error instanceof ErrorEvent) {
					errorMsg = `${error.error.message}`;
				}
				else {
					errorMsg = `${JSON.parse(error.error).message}`;
				}

				console.error(errorMsg);

				return of([]);
			})
		).subscribe(id => {
			this.startingGame = false;

			if ((typeof id) === 'string') {
				console.debug(`recieved game id: ${id}`);

				this.router.navigate(['sudoku', id]).then(nav => {
					console.debug(nav);
				}).catch(err => {
					console.error(err);
				});
			}
		});
	}
}
