import Link from 'next/link';

export default function Home() {
  return (
      <div className="min-h-screen bg-[#F3F4F6] flex items-center justify-center p-4 font-sans">
        <div className="bg-[#FFFFFF] w-full max-w-sm rounded-3xl shadow-lg overflow-hidden flex flex-col relative">

          {/* Cabeçalho com Onda */}
          <div className="bg-[#0D9488] pt-16 pb-12 relative flex-shrink-0">
            <h1 className="text-3xl font-bold text-white text-center relative z-10">Bem-vindo</h1>
            <p className="text-[#F9FAFB] text-center text-sm relative z-10 mt-2 px-6">
              Acesse sua conta ou cadastre-se para começar.
            </p>
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

          {/* Área de Ações */}
          <div className="px-8 pt-8 pb-10 bg-[#FFFFFF] flex-grow flex flex-col gap-4">

            {/* Botão de Login (Destaque Principal) */}
            <Link
                href="/auth/login"
                className="w-full bg-[#0D9488] hover:bg-[#0F766E] text-white font-semibold rounded-2xl py-3 text-center transition-colors shadow-md block"
            >
              Fazer Login
            </Link>

            {/* Botão de Cadastro (Destaque Secundário) */}
            <Link
                href="/auth/register"
                className="w-full bg-[#F9FAFB] hover:bg-[#F3F4F6] border-2 border-[#D1D5DB] text-[#1F2937] font-semibold rounded-2xl py-3 text-center transition-colors block"
            >
              Criar uma Conta
            </Link>

          </div>
        </div>
      </div>
  );
}