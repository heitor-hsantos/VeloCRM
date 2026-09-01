//JWT e autenticação

import { httpClient } from './api';
import { RegisterRequest, RegisterResponse, LoginRequest, LoginResponse } from '@/types/auth';

export const AuthService = {


  async register(data: RegisterRequest): Promise<RegisterResponse> {
    return httpClient<RegisterResponse>('/api/auth/register', {
      method: 'POST',

      body: JSON.stringify(data),
    });
  },

  async login(data: LoginRequest): Promise<LoginResponse> {
    return httpClient<LoginResponse>('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

};