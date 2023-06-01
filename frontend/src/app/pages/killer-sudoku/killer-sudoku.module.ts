import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KillerSudokuRoutingModule } from './killer-sudoku-routing.module';
import { KillerSudokuComponent } from './killer-sudoku.component';
import { SharedModule } from "../../shared/shared.module";


@NgModule({
    declarations: [
        KillerSudokuComponent
    ],
    imports: [
        CommonModule,
        KillerSudokuRoutingModule,
        SharedModule
    ]
})
export class KillerSudokuModule { }
