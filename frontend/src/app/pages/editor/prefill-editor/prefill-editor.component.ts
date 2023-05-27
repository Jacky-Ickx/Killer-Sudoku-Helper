import { Component } from '@angular/core';
import { SudokuService } from 'src/app/core/services/sudoku.service';

@Component({
  selector: 'app-prefill-editor',
  templateUrl: './prefill-editor.component.html',
  styleUrls: ['./prefill-editor.component.scss']
})
export class PrefillEditorComponent {
  constructor(private sudoku: SudokuService) {
    this.sudoku.inputMethod = 'prefill';
    this.sudoku.removeHighlights();
    this.sudoku.enableInput();
  }

  onFinishClick() {
    console.debug(`sending starting grid`)
    this.sudoku.sendAsStartingGrid().subscribe(id => {
        console.debug(`recieved game id: ${id}`);
    })
  }
}
