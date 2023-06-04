import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from "@angular/router";
import { SudokuService } from "src/app/core/services/sudoku/sudoku.service";
import { Observable } from "rxjs";
import { KillerSudokuComponent } from './killer-sudoku.component';
import { SudokuApiService } from '../../core/services/sudoku-api/sudoku-api.service';

@Injectable()
export class KillerSudokuCanDeactivateGuard implements CanDeactivate<KillerSudokuComponent> {
	constructor(private sudoku: SudokuService, private api: SudokuApiService) { }

	canDeactivate(component: KillerSudokuComponent,
		currentRoute: ActivatedRouteSnapshot,
		currentState: RouterStateSnapshot,
		nextState: RouterStateSnapshot
	): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
		this.sudoku.resetState();
		this.api.leaveCurrentSession();
		return true;
	}
}