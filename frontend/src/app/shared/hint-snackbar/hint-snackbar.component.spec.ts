import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HintSnackbarComponent } from './hint-snackbar.component';

describe('HintSnackbarComponent', () => {
  let component: HintSnackbarComponent;
  let fixture: ComponentFixture<HintSnackbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HintSnackbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HintSnackbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
