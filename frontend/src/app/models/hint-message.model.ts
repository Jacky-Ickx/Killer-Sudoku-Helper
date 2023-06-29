import { Coordinates } from './coordinates.model';

export interface HintMessage {
	strategy: string,
	error: boolean,
	row: number | null,
	column: number | null,
	nonet: number | null,
	cells: Coordinates[],
	effect: {
		type: string,
		values: number | number[] | number[][]
	}
}