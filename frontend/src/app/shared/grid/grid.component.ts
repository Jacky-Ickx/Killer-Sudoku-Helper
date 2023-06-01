import { Component, HostListener } from '@angular/core';
import { SudokuService } from 'src/app/core/services/sudoku/sudoku.service';
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
		if (!this.mouseDown) return;
		if (this.sudoku.highlightedCells.some(element => element.x === coordinates.x && element.y === coordinates.y)) return;
		if (this.sudoku.inputMethod === 'prefill') return;

		this.sudoku.highlightCell(coordinates);
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
		console.debug('highlighted cells:', this.sudoku.highlightedCells);
	}

	@HostListener('document:keydown', ['$event'])
	onDocumentKeyDown(event: KeyboardEvent) {
		console.debug(event);
		if ((event.target as HTMLElement).tagName !== 'body'.toUpperCase()) return;

		const numerical: RegExp = /^[0-9]$/;
		if (numerical.test(event.key)) {
			this.handleNumericalKeyDown(event);
			return;
		}

		if (['backspace', 'delete', 'c'].includes(event.key.toLowerCase())) {
			this.handleRemoveKeyDown(event);
			return;
		}

		if (['p'].includes(event.key.toLowerCase())) {
			this.handlePencilMarkToggleDown(event);
			return;
		}
	}

	handleNumericalKeyDown(event: KeyboardEvent) {
		const value = parseInt(event.key);

		if (this.sudoku.inputMethod === 'solve') this.sudoku.toggleValue(value);
		else if (this.sudoku.inputMethod === 'prefill') this.sudoku.setValue(value);
	}

	handleRemoveKeyDown(event: KeyboardEvent) {
		this.sudoku.deleteHighlighted();
	}

	handlePencilMarkToggleDown(event: KeyboardEvent) {
		this.sudoku.togglePencilMarks();
	}
}