'use client';

import React, { useState } from 'react';
import { 
  Search, Plus, Bell, HelpCircle, User, 
  MoreHorizontal, ChevronDown, Lock, Layout, 
  ListTodo, Kanban, ChevronLeft, ChevronRight,
  Mail, Globe, Smartphone, MessageCircle, MessageSquare,
  Paperclip, CheckSquare, Maximize2
} from 'lucide-react';

const INITIAL_DATA = {
  board: {
    id: "board-001",
    title: "Meu quadro do Trello",
    theme: {
      backgroundColor: "#5E3F6B"
    },
    lists: [
      {
        id: "list-1",
        title: "Guia de introdução ao Trello",
        headerColor: "transparent",
        cardCount: 6,
        cards: [
          {
            id: "card-1",
            title: "New to Trello? Start here",
            coverImage: "https://via.placeholder.com/300x150",
            badges: {
              integration: "Loom",
              attachments: 1,
              checklist: { done: 0, total: 6 }
            }
          }
        ]
      },
      {
        id: "list-2",
        title: "Hoje",
        headerColor: "#6A5300",
        cardCount: 0,
        cards: []
      },
      {
        id: "list-3",
        title: "Esta semana",
        headerColor: "#145234",
        cardCount: 0,
        cards: []
      }
    ]
  }
};

export default function KanbanHome() {
  const [board] = useState(INITIAL_DATA.board);
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [activeTab, setActiveTab] = useState('Caixa de entrada');

  return (
    <div className="flex h-screen w-full overflow-hidden font-sans text-white" style={{ backgroundColor: board.theme.backgroundColor }}>
      
      {/* Sidebar - Esquerda */}
      <div 
        className={`${isSidebarOpen ? 'w-64' : 'w-12'} flex-shrink-0 transition-all duration-300 ease-in-out border-r border-white/10 flex flex-col`}
        style={{ backgroundColor: '#0F2547' }}
      >
        <div className="p-3 flex items-center justify-between border-b border-white/10">
          {isSidebarOpen && <span className="font-semibold truncate">Caixa de entrada</span>}
          <button 
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
            className="p-1 hover:bg-white/10 rounded-md text-gray-300"
          >
            {isSidebarOpen ? <ChevronLeft size={18} /> : <ChevronRight size={18} />}
          </button>
        </div>

        {isSidebarOpen && (
          <div className="flex-1 flex flex-col p-3 overflow-y-auto">
            <button className="w-full text-left py-2 px-3 bg-white/10 hover:bg-white/20 rounded-md text-sm font-medium mb-6 transition-colors">
              + Adicionar um cartão
            </button>

            <div className="mb-4">
              <h3 className="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-3">Consolide suas tarefas</h3>
              <div className="flex gap-2 text-gray-400">
                <button className="p-2 bg-white/5 hover:bg-white/10 rounded-md"><Mail size={16} /></button>
                <button className="p-2 bg-white/5 hover:bg-white/10 rounded-md"><Globe size={16} /></button>
                <button className="p-2 bg-white/5 hover:bg-white/10 rounded-md"><Smartphone size={16} /></button>
                <button className="p-2 bg-white/5 hover:bg-white/10 rounded-md"><MessageCircle size={16} /></button>
                <button className="p-2 bg-white/5 hover:bg-white/10 rounded-md"><MessageSquare size={16} /></button>
              </div>
            </div>
          </div>
        )}

        {isSidebarOpen && (
          <div className="p-4 border-t border-white/10 text-xs text-gray-400 flex items-center gap-2">
            <Lock size={14} />
            <span>Visível apenas para você</span>
          </div>
        )}
      </div>

      {/* Área Direita (Header + Canvas) */}
      <div className="flex-1 flex flex-col min-w-0">
        
        {/* Header Superior */}
        <header className="h-14 border-b border-white/10 bg-black/20 flex items-center justify-between px-4 backdrop-blur-sm">
          <div className="flex items-center gap-4">
            <div className="font-bold text-xl tracking-tight flex items-center gap-1">
              <Kanban size={20} className="text-blue-400" />
              <span>Trello</span>
            </div>
            <div className="relative hidden md:block">
              <Search className="absolute left-2.5 top-1.5 text-gray-400" size={16} />
              <input 
                type="text" 
                placeholder="Pesquisar" 
                className="bg-white/10 border border-white/20 rounded-md pl-9 pr-3 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 w-64 text-white placeholder-gray-400"
              />
            </div>
          </div>
          
          <div className="flex items-center gap-3">
            <button className="bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-3 py-1.5 rounded-md flex items-center gap-1 transition-colors">
              <Plus size={16} />
              Criar
            </button>
            <button className="p-1.5 text-gray-300 hover:bg-white/10 rounded-full"><Bell size={18} /></button>
            <button className="p-1.5 text-gray-300 hover:bg-white/10 rounded-full"><HelpCircle size={18} /></button>
            <div className="w-7 h-7 bg-gray-500 rounded-full flex items-center justify-center cursor-pointer border border-white/20">
              <User size={16} />
            </div>
            <button className="bg-white/20 hover:bg-white/30 text-white text-sm font-medium px-3 py-1.5 rounded-md transition-colors ml-2">
              Compartilhar
            </button>
          </div>
        </header>

        {/* Header do Quadro */}
        <div className="px-6 py-3 flex items-center justify-between bg-black/10">
          <div className="flex items-center gap-4">
            <h1 className="text-xl font-bold hover:bg-white/10 px-2 py-1 rounded cursor-pointer">{board.title}</h1>
            <button className="flex items-center gap-1 text-sm bg-white/20 hover:bg-white/30 px-2 py-1 rounded transition-colors">
              <Layout size={14} />
              Quadro
              <ChevronDown size={14} />
            </button>
          </div>
          <button className="p-1.5 hover:bg-white/20 rounded transition-colors">
            <MoreHorizontal size={18} />
          </button>
        </div>

        {/* Main Canvas (Listas) */}
        <div className="flex-1 overflow-x-auto overflow-y-hidden p-6 flex items-start gap-4">
          {board.lists.map((list) => (
            <div 
              key={list.id} 
              className="w-72 flex-shrink-0 flex flex-col rounded-xl max-h-full"
              style={{ backgroundColor: '#2C333A' }} // Fallback background for lists
            >
              {/* List Header */}
              <div 
                className="px-4 py-3 rounded-t-xl flex justify-between items-center group cursor-pointer"
                style={{ backgroundColor: list.headerColor !== 'transparent' ? list.headerColor : 'transparent' }}
              >
                <div className="flex items-center gap-2 font-semibold text-sm">
                  <h2>{list.title}</h2>
                  <span className="text-gray-400 bg-black/20 px-1.5 py-0.5 rounded-full text-xs">
                    {list.cardCount}
                  </span>
                </div>
                <button className="opacity-0 group-hover:opacity-100 p-1 hover:bg-white/20 rounded transition-all">
                  <MoreHorizontal size={16} />
                </button>
              </div>

              {/* List Cards Area */}
              <div className="px-2 py-2 flex-1 overflow-y-auto flex flex-col gap-2 min-h-[50px]">
                {list.cards.map(card => (
                  <div 
                    key={card.id}
                    className="bg-[#22272B] hover:border-white/30 border border-transparent rounded-lg shadow-sm cursor-pointer group relative overflow-hidden"
                  >
                    {card.coverImage && (
                      <div className="h-24 w-full bg-gray-700 relative">
                        {/* eslint-disable-next-line @next/next/no-img-element */}
                        <img 
                          src={card.coverImage} 
                          alt="Cover" 
                          className="w-full h-full object-cover"
                        />
                      </div>
                    )}
                    <div className="p-3">
                      <p className="text-sm text-gray-200 mb-3">{card.title}</p>
                      
                      {card.badges && (
                        <div className="flex flex-wrap items-center gap-2 text-xs text-gray-400">
                          {card.badges.integration && (
                            <span className="bg-white/10 px-2 py-0.5 rounded text-gray-300 font-medium">
                              {card.badges.integration}
                            </span>
                          )}
                          {card.badges.attachments > 0 && (
                            <div className="flex items-center gap-1" title="Anexos">
                              <Paperclip size={12} />
                              <span>{card.badges.attachments}</span>
                            </div>
                          )}
                          {card.badges.checklist && (
                            <div className="flex items-center gap-1" title="Checklist">
                              <CheckSquare size={12} />
                              <span>{card.badges.checklist.done}/{card.badges.checklist.total}</span>
                            </div>
                          )}
                        </div>
                      )}
                    </div>
                    
                    <button className="absolute top-2 right-2 p-1.5 bg-black/50 hover:bg-black/70 rounded opacity-0 group-hover:opacity-100 transition-opacity">
                      <Maximize2 size={14} className="text-white" />
                    </button>
                  </div>
                ))}
              </div>

              {/* Add Card Button */}
              <div className="px-2 py-2 pb-3 mt-auto">
                <button className="w-full text-left px-3 py-2 text-sm text-gray-400 hover:text-gray-200 hover:bg-white/10 rounded-lg flex items-center gap-2 transition-colors">
                  <Plus size={16} />
                  Adicionar um cartão
                </button>
              </div>
            </div>
          ))}

          {/* Add Another List Button */}
          <button className="w-72 flex-shrink-0 bg-white/10 hover:bg-white/20 text-white px-4 py-3 rounded-xl flex items-center gap-2 text-sm font-medium transition-colors">
            <Plus size={16} />
            Adicionar outra lista
          </button>
        </div>
      </div>

      {/* Bottom Dock */}
      <div className="fixed bottom-6 left-1/2 -translate-x-1/2 bg-[#1A2027] border border-white/10 shadow-2xl rounded-full px-2 py-1.5 flex items-center gap-1 z-50">
        {[
          { id: 'Caixa de entrada', icon: ListTodo },
          { id: 'Planejador', icon: Layout },
          { id: 'Quadro', icon: Kanban },
          { id: 'Mudar de quadros', icon: MoreHorizontal }
        ].map(item => (
          <button
            key={item.id}
            onClick={() => setActiveTab(item.id)}
            className={`
              flex items-center gap-2 px-4 py-2 rounded-full text-sm font-medium transition-all
              ${activeTab === item.id 
                ? 'bg-blue-600/20 text-blue-400' 
                : 'text-gray-400 hover:text-gray-200 hover:bg-white/5'}
            `}
          >
            <item.icon size={16} />
            {item.id}
          </button>
        ))}
      </div>
    </div>
  );
}
