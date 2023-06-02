import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from "@angular/router";
import { CageEditorComponent } from "./cage-editor.component";
import { SudokuService } from "src/app/core/services/sudoku/sudoku.service";
import { Observable } from "rxjs";
import { CellContent } from "src/app/models/cell.model";

@Injectable()
export class CageEditorCanDeactivateGuard implements CanDeactivate<CageEditorComponent> {
    constructor(private sudoku: SudokuService) {}

    canDeactivate(component: CageEditorComponent, 
        currentRoute: ActivatedRouteSnapshot, 
        currentState: RouterStateSnapshot, 
        nextState: RouterStateSnapshot
    ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        if(nextState.url === currentState.url.replace(/cages$/g, 'prefill')) { // leaving to next step of editor
            return ([] as CellContent[]).concat(...this.sudoku.grid).every(cell => cell.cage.inCage); // check if all cells are in a cage
        }
        
        if (this.sudoku.cages.length > 0 &&
            !window.confirm('Are you sure, you want to leave the editor?\nALL CHANGES WILL BE LOST, you have been warned')
        ) return false;

        this.sudoku.resetState();
        return true;
    }
}