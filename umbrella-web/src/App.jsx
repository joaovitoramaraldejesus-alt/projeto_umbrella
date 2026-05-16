import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [missoes, setMissoes] = useState([]);

  // Função para buscar as missões do backend
  const carregarMissoes = () => {
    fetch('http://localhost:8080/umbrella/api/missoes')
      .then(res => res.json())
      .then(data => setMissoes(data))
      .catch(err => console.error("Erro ao carregar:", err));
  };

  useEffect(() => {
    carregarMissoes();
  }, []);

  // Função para cadastrar nova missão
  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    fetch('http://localhost:8080/umbrella/api/missoes', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: new URLSearchParams(formData)
    })
    .then(res => {
      if (!res.ok) {
        throw new Error("Erro na resposta do servidor");
      }
      return res;
    })
    .then(() => {
      alert("⚠️ Missão registrada no banco de dados da Umbrella!");
      e.target.reset(); // Limpa o formulário após o sucesso
      carregarMissoes(); // Força o React a atualizar a lista na hora
    })
    .catch(err => {
      console.error("Erro ao enviar:", err);
      // Fallback de segurança: atualiza a lista mesmo se o bloco principal falhar
      carregarMissoes(); 
    });
  };

  return (
    <div className="container">
      <h1>UMBRELLA CORPORATION</h1>
      <p className="subtitle">Painel de Controle de Missões</p>

      {/* Formulário de Cadastro */}
      <div className="card-form">
        <h3>Nova Diretriz de Campo</h3>
        <form onSubmit={handleSubmit}>
          <input name="titulo" placeholder="Título da Missão" required />
          <input name="descricao" placeholder="Descrição dos Objetivos" required />
          <select name="prioridade">
            <option value="BAIXA">Baixa</option>
            <option value="MEDIA">Média</option>
            <option value="ALTA">Alta</option>
          </select>
          <button type="submit">AUTORIZAR MISSÃO</button>
        </form>
      </div>

      <hr />

      {/* Lista de Missões */}
      <h3>Missões Ativas</h3>
      <div className="lista">
        {missoes.map(m => (
          <div key={m.id} className="missao-item">
            <strong>ID: {m.id} - {m.titulo}</strong>
            <p>{m.descricao}</p>
            <div style={{ display: 'flex', gap: '15px', marginTop: '10px' }}>
              {/* Badge da Prioridade */}
              <span className={`badge ${m.prioridade}`}>⚠️ PRIORIDADE: {m.prioridade}</span>
              {/* Badge do Status */}
              <span className="badge-status" style={{ color: m.status === 'ANDAMENTO' ? '#00ff00' : '#ffcc00', fontStyle: 'italic', fontWeight: 'bold' }}>
                ⚙️ STATUS: {m.status || 'PENDENTE'}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default App