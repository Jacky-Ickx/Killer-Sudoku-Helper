import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KillerSudokuComponent } from './killer-sudoku.component';

describe('KillerSudokuComponent', () => {
  let component: KillerSudokuComponent;
  let fixture: ComponentFixture<KillerSudokuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KillerSudokuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KillerSudokuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
