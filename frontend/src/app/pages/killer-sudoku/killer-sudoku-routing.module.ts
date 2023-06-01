import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KillerSudokuComponent } from './killer-sudoku.component';

const routes: Routes = [
    {
        path: ':id',
        component: KillerSudokuComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KillerSudokuRoutingModule { }
