export interface Coordinates {
    x: number,
    y: number,
    [key: string]: any // needed for adjacency in sudoku service
}