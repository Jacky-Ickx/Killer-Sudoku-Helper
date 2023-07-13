import { Component, Inject } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { HintMessage } from '../../models/hint-message.model';
import { SudokuService } from '../../core/services/sudoku/sudoku.service';

@Component({
  selector: 'app-hint-snackbar',
  templateUrl: './hint-snackbar.component.html',
  styleUrls: ['./hint-snackbar.component.scss']
})
export class HintSnackbarComponent {
	public specificity: number = 0;

	constructor(
		private snackBarRef: MatSnackBarRef<HintSnackbarComponent>, 
		@Inject(MAT_SNACK_BAR_DATA) public hint: HintMessage, 
		private sudoku: SudokuService	
	) {}

	showRegion() {
		this.specificity = 1;
		this.sudoku.highlightHintRegion(this.hint.row, this.hint.column, this.hint.nonet);
	}

	showCells() {
		this.specificity = 2;
		this.sudoku.highlightHintAffected(this.hint.cells);
	}

	applySolution() {
		if (!Array.isArray(this.hint.effect.values)) this.sudoku.applyHintValue(this.hint.cells, this.hint.effect.values, this.hint.effect.type === 'set');
		this.dismiss();
	}

	dismiss() {
		this.sudoku.removeHintHighlights();
		this.snackBarRef.dismiss();
	}
}
