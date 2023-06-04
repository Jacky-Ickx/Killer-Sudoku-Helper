import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KillerSudokuComponent } from './killer-sudoku.component';
import { KillerSudokuCanDeactivateGuard } from './deactivate.guard';

const routes: Routes = [
	{
		path: ':id',
		component: KillerSudokuComponent,
		canDeactivate: [KillerSudokuCanDeactivateGuard]
	}
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule],
	providers: [KillerSudokuCanDeactivateGuard]
})
export class KillerSudokuRoutingModule { }
