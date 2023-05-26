import { Injectable } from '@angular/core';
import { Cage } from 'src/app/models/cage.model';
import { CellContent } from 'src/app/models/cell.model';
import { Coordinates } from 'src/app/models/coordinates.model';

@Injectable({
	providedIn: 'root',
})
export class SudokuService {
	public grid: CellContent[][] = [];
	public cages: Cage[] = [];
	public highlightedCells: Coordinates[] = [];
	private inputDisabled: boolean = false;
	public inputMethod: 'prefill' | 'solve' = 'solve';

	possibleSums = [
        [ 1, 9 ],
        [ 3, 17 ],
        [ 6, 24 ],
        [ 10, 30 ],
        [ 15, 35 ],
        [ 21, 39 ],
        [ 28, 42 ],
        [ 36, 44 ],
        [ 45, 45 ]
    ];

	constructor() {
		this.resetState();
	}

	resetState() {
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
						topLeft: false,
					},
				};
			}
		}

		this.cages = [];
		this.highlightedCells = [];
		this.inputDisabled = false;
		this.inputMethod = 'solve';
	}

	removeHighlights() {
		this.highlightedCells.forEach(coordinates => {
			this.grid[coordinates.y][coordinates.x].highlighted = false;
		});

		this.highlightedCells = [];
	}

	highlightCell(coordinates: Coordinates) {
		this.grid[coordinates.y][coordinates.x].highlighted = true;
		this.highlightedCells.push(coordinates);
	}

	disableInput() {
		this.inputDisabled = true;
	}

	enableInput() {
		this.inputDisabled = false;
	}

	toggleValue(value: number) {
		if (this.inputDisabled || this.inputMethod !== 'solve') return;

		const add_value: boolean = this.highlightedCells.some(coordinates => {
            return !this.grid[coordinates.y][coordinates.x].values.includes(value);
		})

		this.highlightedCells.forEach(coordinates => {
            const cell = this.grid[coordinates.y][coordinates.x]
			if(!add_value) {
				let newValues = ([] as number[]).concat(...cell.values)
				newValues = newValues.filter(item => item !== value);
				
				cell.values = newValues; // this is angular change detection bullshit
			}
			else if(!cell.values.includes(value)) {
				let newValues = ([] as number[]).concat(...cell.values)
				newValues.push(value);
				newValues.sort();

				cell.values = newValues; // this is angular change detection bullshit
			}
		});
	}

	setValue(value: number) {
		if (this.inputDisabled || this.inputMethod !== 'prefill') return;

		this.highlightedCells.forEach(coordinates => {
			this.grid[coordinates.y][coordinates.x].values = [value];
		});
	}

    deleteHighlighted() {
        this.highlightedCells.forEach(coordinates => {
			this.grid[coordinates.y][coordinates.x].values = [];
		});
    }

	async cageFromHighlighted(sum: number): Promise<void> {
		if (this.highlightedCells.length < 1 || this.highlightedCells.length > 9) throw new Error('invalid amount of cells selected'); 
		if (sum < this.possibleSums[this.highlightedCells.length-1][0] || 
			sum > this.possibleSums[this.highlightedCells.length-1][1]) throw new Error('sum invalid for selected amount of cells');

		if (this.highlightedCells.some(coordinates => {
			return this.grid[coordinates.y][coordinates.x].cage.inCage;
		})) throw new Error('cages cannot overlap')

		const newCage = {
			sum: sum,
			cells: this.highlightedCells
		};

		this.cages.push(newCage);

		return this.constructCage(newCage);
	}

	async constructCage(cage: Cage): Promise<void> {
		let highestCell = 9;
		let leftmostHighest = 9;

        /* get adjacencies */
		cage.cells.forEach(cell => {
			cell['adjacency'] = {
				top: cage.cells.some(position => position.y === cell.y-1 && position.x === cell.x),
				right: cage.cells.some(position => position.y === cell.y && position.x === cell.x+1),
				bottom: cage.cells.some(position => position.y === cell.y+1 && position.x === cell.x),
				left: cage.cells.some(position => position.y === cell.y && position.x === cell.x-1),
				topRight: cage.cells.some(position => position.y === cell.y-1 && position.x === cell.x+1),
				bottomRight: cage.cells.some(position => position.y === cell.y+1 && position.x === cell.x+1),
				bottomLeft: cage.cells.some(position => position.y === cell.y+1 && position.x === cell.x-1),
				topLeft: cage.cells.some(position => position.y === cell.y-1 && position.x === cell.x-1),
			}
		});
		
		if (cage.cells.length > 1 &&
			cage.cells.some(cell => !cell['adjacency'].top && !cell['adjacency'].right && !cell['adjacency'].bottom && !cell['adjacency'].left)) 
			throw new Error('cells of a cage need to be adjacent to at least one other cell');

        /* construct new grid[y][x].cage values */
		cage.cells.forEach(cell => {
			this.grid[cell.y][cell.x].cage = {
				inCage: true,
				sum: 0,
				top: cell['adjacency'].top,
				right: cell['adjacency'].right,
				bottom: cell['adjacency'].bottom,
				left: cell['adjacency'].left,
				topRight: cell['adjacency'].topRight,
				bottomRight: cell['adjacency'].bottomRight,
				bottomLeft: cell['adjacency'].bottomLeft,
				topLeft: cell['adjacency'].topLeft,
			}
			if (cell.y < highestCell) {
				highestCell = cell.y;
				leftmostHighest = cell.x;
			}
			else if (cell.y === highestCell && cell.x < leftmostHighest) {
				leftmostHighest = cell.x
			}
		});

		this.grid[highestCell][leftmostHighest].cage.sum = cage.sum;
	}
}
