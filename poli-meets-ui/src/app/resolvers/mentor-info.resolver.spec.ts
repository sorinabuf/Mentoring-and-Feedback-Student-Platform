import { TestBed } from '@angular/core/testing';

import { MentorInfoResolver } from './mentor-info.resolver';

describe('MentorInfoResolver', () => {
  let resolver: MentorInfoResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(MentorInfoResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
