package br.com.competeaqui.pix;

/*
 * Classe baseada em uma biblioteca PHP disponível em https://github.com/renatomb/php_qrcode_pix.
 */
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCNumeros;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringValidador;
import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Dados a serem preenchidos pelo usuário para envio de um PIX.
 *
 * @param nomeDestinatario nome do destinatário (máx 25 caracteres)
 * @param chaveDestinatario chave PIX do destinatário
 * @param valor valor a ser transferido (máx 13 caracteres)
 * @param cidadeRemetente cidade de origem do remetente (máx 15 caracteres)
 * @param descricao descrição da transação (opcional)
 * @see QRCodePix
 * @author Manoel Campos da Silva Filho
 * @throws IllegalArgumentException quando é passado um valor inválido para um
 * campo
 * @throws NullPointerException quando algum valor nulo é informado (mesmo para
 * campos opcionais)
 */
public class DadosEnvioPix {

    private String nomeDestinatario;
    private String chaveDestinatario;
    private BigDecimal valor;
    private String cidadeRemetente;
    private String descricao;

    public DadosEnvioPix(String nomeDestinatario, String chaveDestinatario, BigDecimal valor, String cidadeRemetente) {
        this(nomeDestinatario, chaveDestinatario, valor, cidadeRemetente, "");
    }

    public DadosEnvioPix(String pNnomeDestinatario, String pChaveDestinatario, BigDecimal pValor, String pCidadeRemetente, String pDescricao) {
        nomeDestinatario = pNnomeDestinatario;
        chaveDestinatario = pChaveDestinatario;
        valor = pValor;
        cidadeRemetente = pCidadeRemetente;
        descricao = pDescricao;
    }

    public DadosEnvioPix() {
        if (UtilCRCStringValidador.isNuloOuEmbranco(requireNonNull(nomeDestinatario))) {
            throw new IllegalArgumentException("O nome do destinatário é obrigatório.");
        }
        nomeDestinatario = nomeDestinatario.trim();
        if (nomeDestinatario.length() > 25) {
            String msg = "Nome do destinatário não pode ter mais que 25 caracteres. '%s' tem %d caracteres."
                    .format(nomeDestinatario, nomeDestinatario);
            throw new IllegalArgumentException(msg);
        }

        if (UtilCRCStringValidador.isNuloOuEmbranco(requireNonNull(chaveDestinatario))) {
            throw new IllegalArgumentException("A chave PIX do destinatário é obrigatória.");
        }
        chaveDestinatario = chaveDestinatario.trim();
        if (chaveDestinatario.length() > 77) {
            String msg = "Chave PIX do destinatário não pode ter mais que 77 caracteres. '%s' tem %d caracteres."
                    .format(chaveDestinatario, chaveDestinatario.length());
            throw new IllegalArgumentException(msg);
        }

        if (UtilCRCStringValidador.isNuloOuEmbranco(requireNonNull(cidadeRemetente))) {
            throw new IllegalArgumentException("A cidade do remetente é obrigatória.");
        }
        cidadeRemetente = cidadeRemetente.trim();
        if (cidadeRemetente.length() > 15) {
            String msg = "Cidade do remetente não pode ter mais que 15 caracteres. '%s' tem %d caracteres."
                    .format(cidadeRemetente, cidadeRemetente.length());
            throw new IllegalArgumentException(msg);
        }

        requireNonNull(descricao, "A descrição não pode ser nula. Informe um texto vazio no lugar.");
        descricao = descricao.trim();
        if (descricao.length() > 72) {
            String msg = "Descrição não pode ter mais que 72 caracteres. '%s' tem %d caracteres."
                    .format(descricao, descricao.length());
            throw new IllegalArgumentException(msg);
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do PIX deve ser maior que zero.");
        }

        String valorStr = String.valueOf(UtilCRCNumeros.doubleArredondamento(valor.doubleValue(), 2));
        if (valorStr.length() > 13) {
            String msg = "Valor não pode ter mais que 13 caracteres. '%s' tem %d caracteres."
                    .format(valorStr, valorStr.length());
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Obtém um valor incluindo o ponto como separador de decimais e apenas 2
     * casas.
     *
     * @return
     */
    public String valorStr() {
        return String.valueOf(UtilCRCNumeros.doubleArredondamento(valor.doubleValue(), 2));
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public String getChaveDestinatario() {
        return chaveDestinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCidadeRemetente() {
        return cidadeRemetente;
    }

    public String getDescricao() {
        return descricao;
    }

}
