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

	@HostListener('document:mousedown', ['$event'])
	onDocumentMouseDown(event: MouseEvent) {
		if (!this.mouseDown)
		this.highlightedCells.forEach(coordinates => {
			this.grid[coordinates.y][coordinates.x].highlighted = false;
		});

		this.highlightedCells = [];
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

		const add_value: boolean = this.highlightedCells.some(coordinates => {
            return !this.grid[coordinates.y][coordinates.x].values.includes(value);
		})

		this.highlightedCells.forEach(coordinates => {
            const cell = this.grid[coordinates.y][coordinates.x]
			if(!add_value) {
				let new_values = ([] as number[]).concat(...cell.values)
				new_values = new_values.filter(item => item !== value);
				
				cell.values = new_values; // this is angular change detection bullshit
			}
			else if(!cell.values.includes(value)) {
				let new_values = ([] as number[]).concat(...cell.values)
				new_values.push(value);

				cell.values = new_values; // this is angular change detection bullshit
			}
		});
		this.trigger = new Object(); // this is even worse angular change detection bullshit
	}
}