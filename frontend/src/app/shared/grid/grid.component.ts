import { Component, HostListener } from '@angular/core';
import { SudokuService } from 'src/app/core/services/sudoku.service';
import { Cage } from 'src/app/models/cage.model';
import { CellContent } from 'src/app/models/cell.model';
import { Coordinates } from 'src/app/models/coordinates.model';

@Component({
	selector: 'app-grid',
	templateUrl: './grid.component.html',
	styleUrls: ['./grid.component.scss'],
})
export class GridComponent {
	grid: CellContent[][] = [];

	mouseDown: boolean = false;

	constructor(private sudoku: SudokuService) {
		this.grid = sudoku.grid;
	}

	onCellMouseDown(coordinates: Coordinates) {
		this.mouseDown = true;

		this.sudoku.removeHighlights();
		this.sudoku.highlightCell(coordinates);
	}

	onCellMouseOver(coordinates: Coordinates) {
		if (this.mouseDown) {
			if (this.sudoku.highlightedCells.some(element => element.x === coordinates.x && element.y === coordinates.y)) return;

			this.sudoku.highlightCell(coordinates);
		}
	}

	@HostListener('document:mousedown', ['$event'])
	onDocumentMouseDown(event: MouseEvent) { 
		const target = event.target as HTMLElement;
		if (!this.mouseDown && (
			target.tagName === 'app-content-pane'.toUpperCase() || target.parentElement?.tagName === 'app-content-pane'.toUpperCase()
		)) {
			this.sudoku.removeHighlights();
		}
	}

	@HostListener('document:mouseup', ['$event'])
	onDocumentMouseUp(event: MouseEvent) {
		this.mouseDown = false;
		console.debug(this.sudoku.highlightedCells);
	}

	@HostListener('document:keypress', ['$event'])
	onDocumentKeyPress(event: KeyboardEvent) {
		console.debug(event);
		if ((event.target as HTMLElement).tagName !== 'body'.toUpperCase()) return;

		const numerical: RegExp = /^[0-9]$/;
		if (!numerical.test(event.key)) return;
		
		const value = parseInt(event.key);

		this.sudoku.toggleValue(value);
	}
}