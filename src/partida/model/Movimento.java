package partida.model;

import enums.TiposMovimentoEspecial;
import partida.model.pecas.Peca;

public class Movimento {

    private Casa casaInicial, casaFinal;
    private TiposMovimentoEspecial tipo = TiposMovimentoEspecial.NORMAL;
    private String argumentoOpcional = null;

    public Movimento(Casa casaInicial, Casa casaFinal) {
        this.casaInicial = casaInicial;
        this.casaFinal = casaFinal;
    }

    public Movimento(Tabuleiro tabuleiro, Movimento movimento) {
        Casa casas[][] = tabuleiro.getCasas();

        Casa casaMovimentoInicial = movimento.getCasaInicial();
        Casa casaMovimentoFinal = movimento.getCasaFinal();

        this.casaInicial = casas[casaMovimentoInicial.getCoordenada().getY()][casaMovimentoInicial.getCoordenada().getX()];
        this.casaFinal = casas[casaMovimentoFinal.getCoordenada().getY()][casaMovimentoFinal.getCoordenada().getX()];
        this.tipo = movimento.getTipo();
        this.argumentoOpcional = movimento.getArgumentoOpcional();
    }

    public Movimento(Casa casaInicial, Casa casaFinal, String opcional) {
        this(casaInicial, casaFinal);
        this.argumentoOpcional = opcional;
    }

    public Movimento(String movimento, Tabuleiro tabuleiro) {
        String primeiraCasa = movimento.substring(0, 2);
        String segundaCasa = movimento.substring(2, 4);
        
        if (movimento.length() == 5) {
            String pecaCoroacao = movimento.substring(5, 5);
            this.argumentoOpcional = pecaCoroacao;
        }

        Casa primeiraCasaConvertida = null, segundaCasaConvertida = null;
        Casa casas[][] = tabuleiro.getCasas();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Casa casa = casas[y][x];
                if (casa.toString().equals(primeiraCasa)) {
                    primeiraCasaConvertida = casa;
                }

                if (casa.toString().equals(segundaCasa)) {
                    segundaCasaConvertida = casa;
                }

                if (primeiraCasaConvertida != null && segundaCasaConvertida != null) {
                    break;
                }
            }
        }

        Movimento movimentoConvertido = tabuleiro.getMovimento(primeiraCasaConvertida, segundaCasaConvertida);

        this.casaInicial = movimentoConvertido.getCasaInicial();
        this.casaFinal = movimentoConvertido.getCasaFinal();
        this.tipo = movimentoConvertido.getTipo();
    }

    public Casa getCasaFinal() {
        return casaFinal;
    }

    public void setCasaFinal(Casa casaFinal) {
        this.casaFinal = casaFinal;
    }

    public Casa getCasaInicial() {
        return casaInicial;
    }

    public void setCasaInicial(Casa casaInicial) {
        this.casaInicial = casaInicial;
    }

    public String getArgumentoOpcional() {
        return argumentoOpcional;
    }

    public void setArgumentoOpcional(String argumentoOpcional) {
        this.argumentoOpcional = argumentoOpcional;
    }

    public static boolean validarPrimeiraSelecao(Casa primeiraCasaSelecionada) {
        return !primeiraCasaSelecionada.estaVazia() && primeiraCasaSelecionada.getPeca().getCor() == primeiraCasaSelecionada.getTabuleiro().getTurno();
    }

    public static boolean validarSegundaSelecao(Casa primeiraCasaSelecionada, Casa segundaCasaSelecionada) {
        return (primeiraCasaSelecionada != null && (segundaCasaSelecionada.estaVazia() || segundaCasaSelecionada.getPeca().getCor() != primeiraCasaSelecionada.getPeca().getCor()));
    }

    public boolean validarSelecao() {
        return validarPrimeiraSelecao(casaInicial) && validarSegundaSelecao(casaInicial, casaFinal);
    }

    public Peca getPeca() {
        if (casaInicial.estaVazia()) {
            return null;
        }
        return casaInicial.getPeca();
    }

    @Override
    public String toString() {
        return casaInicial.toString() + casaFinal.toString();
    }

    public boolean eEspecial() {
        return tipo != TiposMovimentoEspecial.NORMAL;
    }

    public TiposMovimentoEspecial getTipo() {
        return tipo;
    }

    public void setTipo(TiposMovimentoEspecial tipo) {
        this.tipo = tipo;
    }

}
