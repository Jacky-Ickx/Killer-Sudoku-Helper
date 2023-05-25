import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CellContent } from 'src/app/models/cell.model';
import { Coordinates } from 'src/app/models/coordinates.model';

@Component({
	selector: 'app-cell',
	templateUrl: './cell.component.html',
	styleUrls: ['./cell.component.scss']
})

export class CellComponent{
	@Input() content!: CellContent;
	@Input() x!: number;
	@Input() y!: number;

	@Input() trigger: any;

	@Output() mouseDown = new EventEmitter<Coordinates>();
	@Output() mouseOver = new EventEmitter<Coordinates>();

	onMouseDown(event: MouseEvent) {
		event.preventDefault();
		this.mouseDown.emit({x: this.x, y: this.y});
	}

	onMouseOver(event: MouseEvent) {
		event.preventDefault();
		this.mouseOver.emit({x: this.x, y: this.y});
	}
}
