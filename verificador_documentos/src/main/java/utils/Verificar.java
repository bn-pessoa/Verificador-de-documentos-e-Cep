package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import common.MsgResp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.*;

public class Verificar{
    public boolean verificarRG(long numDoc, MsgResp resp){
        try {
            Scanner buscaRG = new Scanner(new FileReader("src/main/java/databases/bdRG.txt"));
            Long[] nRG = new Long[20];
            for(int i=0; i<10; i++){
                nRG[i] = buscaRG.nextLong();
                if (numDoc == nRG[i].longValue()) {
                    resp.setAdicionalInfo("O rg está no nosso banco de dados.");
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String rgStr = Long.toString(numDoc);
            Integer[] rg = new Integer[9];
            int result = 0;

            for (int i = 0; i < 9; i++) {
                rg[i] = Integer.parseInt(String.valueOf(rgStr.charAt(i)));
            }
            result = (2 * rg[0]) + (3 * rg[1]) + (4 * rg[2]) + (5 * rg[3]) + (6 * rg[4]) + (7 * rg[5]) +
                    (8 * rg[6]) + (9 * rg[7]) + (100 * rg[8]);

            if (result % 11 == 0) return true;
            else return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean verificarTituloEleitor(Long doc, MsgResp resp) {
        try {
            Scanner buscaTitulo = new Scanner(new FileReader("src/main/java/databases/bdTitulo.txt"));
            Long[] nTitulo = new Long[20];
            for(int i=0; i<10; i++){
                nTitulo[i] = buscaTitulo.nextLong();
                if (doc == nTitulo[i].longValue()) {
                    resp.setAdicionalInfo("O titulo está no nosso banco de dados.");
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String documento = Long.toString(doc);
        int N1;
        int N2;
        int N3;
        int N4;
        int N5;
        int N6;
        int N7;
        int N8;
        int N9;
        int N10;
        int N11;
        int N12;
        int DG1;
        int DG2;
        int qN;
        if (documento.length() == 0) {
            return false;
        } else {
            if (documento.length() < 12) {
                documento = "000000000000" + documento;
                documento = documento.substring(documento.length() - 12);
            } else if (documento.length() > 12) {
                return false;
            }
        }

        qN = documento.length();

        N1 = Integer.parseInt(Mid(documento, qN - 11, 1));
        N2 = Integer.parseInt(Mid(documento, qN - 10, 1));
        N3 = Integer.parseInt(Mid(documento, qN - 9, 1));
        N4 = Integer.parseInt(Mid(documento, qN - 8, 1));
        N5 = Integer.parseInt(Mid(documento, qN - 7, 1));
        N6 = Integer.parseInt(Mid(documento, qN - 6, 1));
        N7 = Integer.parseInt(Mid(documento, qN - 5, 1));
        N8 = Integer.parseInt(Mid(documento, qN - 4, 1));
        N9 = Integer.parseInt(Mid(documento, qN - 3, 1));
        N10 = Integer.parseInt(Mid(documento, qN - 2, 1));
        N11 = Integer.parseInt(Mid(documento, qN - 1, 1));
        N12 = Integer.parseInt(Mid(documento, qN, 1));

        DG1 = (N1 * 2) + (N2 * 3) + (N3 * 4) + (N4 * 5) + (N5 * 6) +
                (N6 * 7) + (N7 * 8) + (N8 * 9);
        DG1 = DG1 % 11;

        if (DG1 == 10) {
            DG1 = 0;
        }

        DG2 = (N9 * 7) + (N10 * 8) + (DG1 * 9);
        DG2 = DG2 % 11;

        if (DG2 == 10) {
            DG2 = 0;
        }

        if (N11 == DG1 && N12 == DG2) {
            return true;
        } else {
            return false;
        }
    }
    public static String Mid(String txt, int i, int tam) {
        return txt.substring(i - 1, i + (tam - 1));
    }

    public boolean verificarCEP(Long cep, MsgResp resp){
        String numCep = Long.toString(cep);
        if(numCep.length() == 7) {
            numCep = "0" + numCep;
        }
        if (numCep.length() == 8){
            numCep = numCep.substring(0, 8);
            if(Pattern.matches("[0-9]{5}[0-9]{3}", numCep)){
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(
                            "https://viacep.com.br/ws/"+numCep+"/json"
                    ).openConnection();

                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL ");
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                    String output = "";
                    String line;
                    while ((line = br.readLine()) != null) {
                        output += line;
                    }

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(output);

                    JSONObject jObj = (JSONObject)obj;
                    if(jObj.get("erro") != null) return false;

                    String infoCEP = "Bairro: " + jObj.get("bairro") +
                            "\nLocal: " + jObj.get("localidade") + " - " +
                            jObj.get("uf") + "\nRua: " + jObj.get("logradouro");

                    resp.setAdicionalInfo(infoCEP);

                    conn.disconnect();
                }
                catch(Exception e){
                    System.out.println("Erro ao obter dados da URL");
                }

                return true;
            }
        }

        return false;
    }

    public boolean verificarCPF(Long doc, MsgResp resp) {
        try {
            Scanner buscaCPF = new Scanner(new FileReader("src/main/java/databases/bdCPF.txt"));
            Long[] nCPF = new Long[20];
            for(int i=0; i<10; i++){
                nCPF[i] = buscaCPF.nextLong();
                if (doc == nCPF[i].longValue()) {
                    resp.setAdicionalInfo("O cpf está no nosso banco de dados.");
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String documento = Long.toString(doc);
        if (documento.equals("00000000000") || documento.equals("11111111111") ||
                documento.equals("22222222222") || documento.equals("33333333333") ||
                documento.equals("44444444444") || documento.equals("55555555555") ||
                documento.equals("66666666666") || documento.equals("77777777777") ||
                documento.equals("88888888888") || documento.equals("99999999999") ||
                (documento.length() != 11)) {
            return false;
        } else {
            char dig10, dig11;
            int x, i, r, num, peso;

            try {
                x = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {

                    num = (int) (documento.charAt(i) - 48);
                    x = x + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (x % 11);
                if ((r == 10) || (r == 11)) {
                    dig10 = '0';
                } else {
                    dig10 = (char) (r + 48);
                }
                x = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = (int) (documento.charAt(i) - 48);
                    x = x + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (x % 11);
                if ((r == 10) || (r == 11)) {
                    dig11 = '0';
                }

                else {
                    dig11 = (char) (r + 48);
                }
                if ((dig10 == documento.charAt(9)) && (dig11 == documento.charAt(10))) {
                    return true;
                }

                else {
                    return false;
                }
            } catch (Exception erro) {
                return false;
            }
        }
    }

    public boolean verificarCPNJ(Long CNPJ, MsgResp resp){
        try {
            Scanner buscaCNPJ = new Scanner(new FileReader("src/main/java/databases/bdCNPJ.txt"));
            Long[] nCNPJ = new Long[20];
            for(int i=0; i<10; i++){
                nCNPJ[i] = buscaCNPJ.nextLong();
                if (CNPJ == nCNPJ[i].longValue()) {
                    resp.setAdicionalInfo("O cnpj está no nosso banco de dados.");
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String strCNPJ = Long.toString(CNPJ);
        if (strCNPJ.equals("00000000000000") | strCNPJ.equals("11111111111111") |
                strCNPJ.equals("22222222222222") | strCNPJ.equals("33333333333333") |
                strCNPJ.equals("44444444444444") | strCNPJ.equals("55555555555555") |
                strCNPJ.equals("66666666666666") | strCNPJ.equals("77777777777777") |
                strCNPJ.equals("88888888888888") | strCNPJ.equals("99999999999999") |
                (strCNPJ.length() != 14)){
            return(false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
                num = (int)(strCNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) | (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(strCNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) | (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

            if ((dig13 == strCNPJ.charAt(12)) && (dig14 == strCNPJ.charAt(13)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}

