'use client';

import Link from 'next/link';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { AuthService } from '@/services/authService';

export default function LoginPage() {
    const router = useRouter();
    
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const handleLogin = async (formData: FormData) => {
        setIsLoading(true);
        setError('');

        const email = formData.get('email') as string;
        const password = formData.get('password') as string;

        try {
            const response = await AuthService.login({ email, password });
            console.log('Login com sucesso:', response);
            if (response.token) {
                localStorage.setItem('token', response.token);
            }
            router.push('/');
        } catch (err: any) {
            console.error('Erro ao fazer login:', err);
            setError(err.message || 'Credenciais inválidas. Tente novamente.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-[#F3F4F6] flex items-center justify-center p-4 font-sans">
            <div className="bg-[#FFFFFF] w-full max-w-sm rounded-3xl shadow-lg overflow-hidden flex flex-col relative">

                {/* Cabeçalho com Onda */}
                <div className="bg-[#0D9488] pt-16 pb-12 relative flex-shrink-0">
                    <h1 className="text-3xl font-bold text-white text-center relative z-10">Login</h1>
                    <div className="absolute bottom-0 left-0 w-full overflow-hidden leading-[0] translate-y-[1px]">
                        <svg
                            className="relative block w-full h-[60px]"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 1440 320"
                            preserveAspectRatio="none"
                        >
                            <path
                                fill="#FFFFFF"
                                fillOpacity="1"
                                d="M0,192L48,170.7C96,149,192,107,288,112C384,117,480,171,576,192C672,213,768,203,864,170.7C960,139,1056,85,1152,74.7C1248,64,1344,96,1392,112L1440,128L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
                            ></path>
                        </svg>
                    </div>
                </div>

                {/* Formulário */}
                <div className="px-8 pt-2 pb-8 bg-[#FFFFFF] flex-grow">
                    <form className="space-y-4" action={handleLogin}>
                        {error && (
                            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative text-sm" role="alert">
                                <span className="block sm:inline">{error}</span>
                            </div>
                        )}
                        <div>
                            <label className="block text-sm font-medium text-[#1F2937] mb-1 ml-1">Email</label>
                            <input
                                name="email"
                                type="email"
                                placeholder="mail@example.com"
                                className="w-full bg-[#F9FAFB] border border-[#D1D5DB] rounded-2xl px-4 py-3 outline-none focus:border-[#0D9488] focus:ring-1 focus:ring-[#0D9488] transition-all text-[#1F2937]"
                                required
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-[#1F2937] mb-1 ml-1">Password</label>
                            <input
                                name="password"
                                type="password"
                                placeholder="••••••••"
                                className="w-full bg-[#F9FAFB] border border-[#D1D5DB] rounded-2xl px-4 py-3 outline-none focus:border-[#0D9488] focus:ring-1 focus:ring-[#0D9488] transition-all text-[#1F2937]"
                                required
                            />
                        </div>

                        <div className="flex justify-end pb-4">
                            <a href="#" className="text-xs text-[#1F2937] hover:text-[#0D9488] transition-colors">
                                Forgot Password?
                            </a>
                        </div>

                        <button
                            type="submit"
                            disabled={isLoading}
                            className="w-full bg-[#0D9488] hover:bg-[#0F766E] disabled:bg-[#0D9488]/50 disabled:cursor-not-allowed text-white font-semibold rounded-2xl py-3 transition-colors shadow-md"
                        >
                            {isLoading ? 'Logging in...' : 'Log In'}
                        </button>
                    </form>

                    <div className="mt-6 text-center text-sm text-[#1F2937]">
                        Don't have an account? <Link href="/auth/register" className="text-[#0D9488] font-bold hover:underline">Sign Up</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}