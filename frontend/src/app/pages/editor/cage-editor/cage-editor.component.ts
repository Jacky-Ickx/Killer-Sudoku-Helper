import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SudokuService } from 'src/app/core/services/sudoku/sudoku.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
	selector: 'app-cage-editor',
	templateUrl: './cage-editor.component.html',
	styleUrls: ['./cage-editor.component.scss'],
})
export class CageEditorComponent {
	sumFormControl = new FormControl('', [Validators.required, Validators.min(1), Validators.max(45)]);

	constructor(
		private router: Router, 
		private activeRoute: ActivatedRoute, 
		private sudoku: SudokuService,
		private snackbar: MatSnackBar
	) {
		this.sudoku.resetState();
		this.sudoku.disableInput();
	}

	onNextClick() {
		this.router.navigate(['prefill'], {relativeTo: this.activeRoute.parent}).then(nav => {
			console.debug(nav);
		}).catch(err => {
			console.error(err);
		});
	}

	createCage() {
		this.sudoku.cageFromHighlighted(parseInt(this.sumFormControl.value!)).then(() => {
			console.debug('cage created successfully');
			this.sumFormControl.reset();
		}).catch((err: Error) => {
			console.error(err);
			this.snackbar.open(err.message, 'dismiss');
		});
	}
}
