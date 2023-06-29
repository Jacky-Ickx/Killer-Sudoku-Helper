import { Component, Inject } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { HintMessage } from '../../models/hint-message.model';

@Component({
  selector: 'app-hint-snackbar',
  templateUrl: './hint-snackbar.component.html',
  styleUrls: ['./hint-snackbar.component.scss']
})
export class HintSnackbarComponent {
	public specificity: number = 0;

	constructor(public snackBarRef: MatSnackBarRef<HintSnackbarComponent>, @Inject(MAT_SNACK_BAR_DATA) public hint: HintMessage) {}

	showRegion() {
		this.specificity = 1;
	}

	showCells() {
		this.specificity = 2;
	}

	applySolution() {
		this.snackBarRef.dismiss();
	}
}
