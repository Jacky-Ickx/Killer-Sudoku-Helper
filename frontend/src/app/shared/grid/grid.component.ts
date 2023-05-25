import { ChangeDetectorRef, Component, HostListener } from '@angular/core';
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
    cages: Cage[] = [];
	highlightedCells: Coordinates[] = [];

	mouseDown: boolean = false;
	trigger: any = undefined;

	constructor(private changeDetector: ChangeDetectorRef) {
		for (let y = 0; y < 9; y++) {
			this.grid[y] = [];
			for (let x = 0; x < 9; x++) {
				this.grid[y][x] = { 
                    values: [], 
                    isPencilMark: false, 
                    highlighted: false,
                    cage: {
                        inCage: false,
                        sum: 0,
                        top: false,
                        right: false,
                        bottom: false,
                        left: false,
                        topRight: false,
                        bottomRight: false,
                        bottomLeft: false,
                        topLeft: false
                    }
                };
			}
		}
	}

	onCellMouseDown(coordinates: Coordinates) {
		this.highlightedCells.forEach(coordinates => {
			this.grid[coordinates.y][coordinates.x].highlighted = false;
		});

		this.highlightedCells = [];

		// console.log(event);
		console.log(coordinates)
		this.grid[coordinates.y][coordinates.x].highlighted = true;
		this.highlightedCells.push(coordinates);

		this.mouseDown = true;
	}

	onCellMouseOver(coordinates: Coordinates) {
		if (this.mouseDown) {
			if (this.highlightedCells.some(element => element.x === coordinates.x && element.y === coordinates.y)) return;
			this.grid[coordinates.y][coordinates.x].highlighted = true;
			this.highlightedCells.push(coordinates);
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
		this.highlightedCells.forEach(coordinates => {
            const cell = this.grid[coordinates.y][coordinates.x]
			let new_vals = [value]
			new_vals.push(...cell.values);

			cell.values = new_vals; // this is angular change detection bullshit
		});
		this.trigger = new Object(); // this is even worse angular change detection bullshit
	}
}