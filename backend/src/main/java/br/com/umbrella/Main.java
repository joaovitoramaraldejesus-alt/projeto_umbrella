package br.com.umbrella;

import br.com.umbrella.dao.MissaoDAO;
import br.com.umbrella.model.Missao;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("\n=== INICIANDO SISTEMA UMBRELLA CORP ===");
            
            MissaoDAO dao = new MissaoDAO();
            List<Missao> lista = dao.listarTodas();

            if (lista.isEmpty()) {
                System.out.println("Aviso: O banco de dados está vazio ou a conexão falhou.");
            } else {
                System.out.println("Conexão bem-sucedida! Missões encontradas:");
                for (Missao m : lista) {
                    System.out.println("------------------------------------");
                    System.out.println("ID: " + m.getId());
                    System.out.println("Título: " + m.getTitulo());
                    System.out.println("Status: " + m.getStatus());
                }
                System.out.println("------------------------------------");
            }
            
        } catch (Exception e) {
            System.out.println("ERRO AO EXECUTAR O MAIN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}