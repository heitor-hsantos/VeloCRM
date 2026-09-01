// API client for VeloCRM
//conexão com o CORE backend do CRM

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

export async function httpClient<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;

    const defaultHeaders = {
        // Dizemos ao Java que o corpo da requisição é um JSON
        'Content-Type': 'application/json',
    };


    const response = await fetch(url, {
        ...options,
        headers: {
            ...defaultHeaders,
            ...options.headers,
        },
    });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'An error occurred while fetching data.');
  }

  return response.json() as Promise<T>;
}