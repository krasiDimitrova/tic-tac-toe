import { TestBed, inject } from '@angular/core/testing';

import { GameApiService } from './game-api.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('GameServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GameApiService],
    });
  });

  it('should be created', inject([GameApiService], (service: GameApiService) => {
    expect(service).toBeTruthy();
  }));
});
