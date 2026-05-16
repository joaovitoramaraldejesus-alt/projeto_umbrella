package br.com.umbrella.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.umbrella.dao.MissaoDAO;
import br.com.umbrella.model.Missao;

@WebServlet("/api/missoes")
public class MissaoController extends HttpServlet {

    private MissaoDAO dao = new MissaoDAO();
    private Gson gson = new Gson();

    // BUSCAR MISSÕES
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORREÇÃO: Libera o CORS para o React conseguir ler os dados do GET
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

        List<Missao> lista = dao.listarTodas();
        String json = gson.toJson(lista);
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
    }

    // CADASTRAR NOVA MISSÃO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORREÇÃO: Libera o CORS para o React receber a confirmação do POST com sucesso
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        
        // Garante a acentuação correta vinda do formulário
        req.setCharacterEncoding("UTF-8");

        // Pega os dados do formulário
        String titulo = req.getParameter("titulo");
        String descricao = req.getParameter("descricao");
        String prioridade = req.getParameter("prioridade");

        Missao nova = new Missao();
        nova.setTitulo(titulo);
        nova.setDescricao(descricao);
        nova.setPrioridade(prioridade);
        
        // CORREÇÃO: Define como "ANDAMENTO" para aparecer direto na lista ativa do site!
        nova.setStatus("ANDAMENTO"); 

        dao.salvar(nova); // Salva no banco

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    // CORREÇÃO AUXILIAR: Navegadores às vezes enviam uma requisição "OPTIONS" antes do POST para checar o CORS
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}