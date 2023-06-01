import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMessage } from '@stomp/rx-stomp';
import { SudokuService } from 'src/app/core/services/sudoku.service';
import { RxStompService } from 'src/app/rx-stomp.service';

@Component({
	selector: 'app-killer-sudoku',
	templateUrl: './killer-sudoku.component.html',
	styleUrls: ['./killer-sudoku.component.scss']
})
export class KillerSudokuComponent implements OnInit {
	id!: string;

	constructor(private route: ActivatedRoute, private rxStompService: RxStompService, private sudoku: SudokuService) { 
		this.sudoku.resetState();
	}

	ngOnInit() {
		this.id = this.route.snapshot.paramMap.get('id')!;

		this.rxStompService.watch(`/session/broker/${this.id}`).subscribe(message => this.handleMessage(message));

		this.rxStompService.publish({
			destination: `/session/handler/${this.id}/hello`,
			body: 'test message'
		})
	}

	handleMessage(message: IMessage) {
		console.debug(message.body);
	}
}