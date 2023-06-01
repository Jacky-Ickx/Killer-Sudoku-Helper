import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { SudokuService } from '../sudoku/sudoku.service';

@Injectable({
	providedIn: 'root'
})
export class SudokuApiService {
	private basePath = `${environment.protocol}://${environment.backend_url}`;

	constructor(private sudoku: SudokuService, private httpClient: HttpClient) { }

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
}
