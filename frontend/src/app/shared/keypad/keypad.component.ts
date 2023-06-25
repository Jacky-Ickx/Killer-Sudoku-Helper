import { Component } from '@angular/core';
import { SudokuApiService } from 'src/app/core/services/sudoku-api/sudoku-api.service';

@Component({
	selector: 'app-keypad',
	templateUrl: './keypad.component.html',
	styleUrls: ['./keypad.component.scss']
})
export class KeypadComponent {
	constructor(private api: SudokuApiService) {}

	requestHelp() {
		console.log('requestHelp');
		this.api.requestHint();
	}
}

