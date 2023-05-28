import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrefillEditorComponent } from './prefill-editor.component';

describe('PrefillEditorComponent', () => {
  let component: PrefillEditorComponent;
  let fixture: ComponentFixture<PrefillEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrefillEditorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrefillEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
