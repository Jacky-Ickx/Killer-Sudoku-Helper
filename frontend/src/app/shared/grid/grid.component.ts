import { ChangeDetectorRef, Component, HostListener } from '@angular/core';
import { CellContent } from 'src/app/models/cell.model';
import { Coordinates } from 'src/app/models/coordinates.model';

@Component({
	selector: 'app-grid',
	templateUrl: './grid.component.html',
	styleUrls: ['./grid.component.scss'],
})
export class GridComponent {
	grid: CellContent[][] = [];
	highlightedCells: CellContent[] = [];
	mouseDown: boolean = false;
	trigger: any = undefined;

	constructor(private changeDetector: ChangeDetectorRef) {
		for (let y = 0; y < 9; y++) {
			this.grid[y] = [];
			for (let x = 0; x < 9; x++) {
				this.grid[y][x] = { values: [], isPencilMark: false, highlighted: false };
			}
		}
	}

	onCellMouseDown(coordinates: Coordinates) {
		this.highlightedCells.forEach(cell => {
			cell.highlighted = false;
		});

		this.highlightedCells = [];

		// console.log(event);
		console.log(coordinates)
		const cell: CellContent = this.grid[coordinates.y][coordinates.x]
		cell.highlighted = true;
		this.highlightedCells.push(cell);

		this.mouseDown = true;
	}

	onCellMouseOver(coordinates: Coordinates) {
		if (this.mouseDown) {
			const cell: CellContent = this.grid[coordinates.y][coordinates.x]
			if (this.highlightedCells.includes(cell)) return;
			cell.highlighted = true;
			this.highlightedCells.push(cell);
		}
	}

	@HostListener('document:mouseup', ['$event'])
	onDocumentMouseUp(event: MouseEvent) {
		this.mouseDown = false;
		console.log(this.highlightedCells);
	}

	@HostListener('document:keypress', ['$event'])
	onDocumentKeyPress(event: KeyboardEvent) {
		console.log(event);

		const numerical: RegExp = /^[0-9]$/;
		if (!numerical.test(event.key)) return;
		
		const value = parseInt(event.key);
		this.highlightedCells.forEach(cell => {
			let new_vals = [value]
			new_vals.push(...cell.values);

			cell.values = new_vals; // this is angular change detection bullshit
		});
		this.trigger = new Object(); // this is even worse angular change detection bullshit
	}
}