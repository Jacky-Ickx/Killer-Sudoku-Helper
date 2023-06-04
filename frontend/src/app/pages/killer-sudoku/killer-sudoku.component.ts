import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SudokuApiService } from 'src/app/core/services/sudoku-api/sudoku-api.service';
import { SudokuService } from 'src/app/core/services/sudoku/sudoku.service';

@Component({
	selector: 'app-killer-sudoku',
	templateUrl: './killer-sudoku.component.html',
	styleUrls: ['./killer-sudoku.component.scss']
})
export class KillerSudokuComponent implements OnInit {
	id!: string;

	constructor(
		private route: ActivatedRoute,
		private sudoku: SudokuService,
		private api: SudokuApiService
	) {
		this.sudoku.resetState();
	}

	ngOnInit() {
		this.id = this.route.snapshot.paramMap.get('id')!;

		this.api.applyCurrentState(this.id);
	}
}
