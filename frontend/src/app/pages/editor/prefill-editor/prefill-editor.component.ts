import { Component } from '@angular/core';
import { SudokuService } from 'src/app/core/services/sudoku/sudoku.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SudokuApiService } from '../../../core/services/sudoku-api/sudoku-api.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
	selector: 'app-prefill-editor',
	templateUrl: './prefill-editor.component.html',
	styleUrls: ['./prefill-editor.component.scss']
})
export class PrefillEditorComponent {
	startingGame: boolean = false;

	constructor(
		private sudoku: SudokuService, 
		private api: SudokuApiService, 
		private router: Router,
		private snackbar: MatSnackBar
	) {
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
				if(error.status === 400) this.snackbar.open('invalid grid', 'dismiss', {panelClass: 'error'});

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
