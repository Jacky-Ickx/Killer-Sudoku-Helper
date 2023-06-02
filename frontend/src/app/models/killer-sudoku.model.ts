import { Cage } from "./cage.model";
import { CellContent } from "./cell.model";

export interface KillerSudoku {
	cages: Cage[],
	grid: CellContent[][]
}