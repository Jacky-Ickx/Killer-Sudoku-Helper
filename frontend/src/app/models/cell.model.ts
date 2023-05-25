export interface CellContent {
    values: number[],
    isPencilMark: boolean,
    highlighted: boolean,
    cage: {
        inCage: boolean, // needed for editor
        sum: number, // 0 if this cell doesn't display the cage-sum
        /* directions of adjacent cells and if they are in the same cage */
        top: boolean, 
        right: boolean, 
        bottom: boolean,
        left: boolean,
        topRight: boolean,
        bottomRight: boolean,
        bottomLeft: boolean,
        topLeft: boolean,
    }
};