import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeQuery } from './employee-query';

describe('EmployeeQuery', () => {
  let component: EmployeeQuery;
  let fixture: ComponentFixture<EmployeeQuery>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeQuery]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeQuery);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
