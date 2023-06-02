import { Coordinates } from './coordinates.model';

export interface ActionMessage {
	actionType: string,
	cells: Coordinates[],
	value: number
}