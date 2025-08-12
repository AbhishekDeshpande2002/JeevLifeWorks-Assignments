import { TestBed } from '@angular/core/testing';

import { DashboardAnalytics } from './dashboard-analytics';

describe('DashboardAnalytics', () => {
  let service: DashboardAnalytics;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DashboardAnalytics);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
