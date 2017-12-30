/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.presentation;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author peof
 */
public class FormularioResposta {
    
    Boolean concordoTotalmente;
    Boolean concordo;
    Boolean naoConcordoNemDiscordo;
    Boolean discordo;
    Boolean discordoTotalmente;
    Boolean naoSeiAvaliar;
    Integer questaoCodigo;
    String questaoTexto;
    Integer topicoCodigo;
    
    public FormularioResposta(Integer topicoCodigo, Integer questaoCodigo, String questaoTexto, Boolean concordoTotalmente, Boolean concordo, Boolean naoConcordoNemDiscordo, Boolean discordo, Boolean discordoTotalmente, Boolean naoSeiAvaliar) {
        this.topicoCodigo = topicoCodigo;
        this.questaoCodigo = questaoCodigo;
        this.questaoTexto = questaoTexto;
        this.concordoTotalmente = concordoTotalmente;
        this.concordo = concordo;
        this.naoConcordoNemDiscordo = naoConcordoNemDiscordo;
        this.discordo = discordo;
        this.discordoTotalmente = discordoTotalmente;
        this.naoSeiAvaliar = naoSeiAvaliar;
    }    

    public Integer getTopicoCodigo() {
        return topicoCodigo;
    }

    public void setTopicoCodigo(Integer topicoCodigo) {
        this.topicoCodigo = topicoCodigo;
    }

    public Integer getQuestaoCodigo() {
        return questaoCodigo;
    }

    public void setQuestaoCodigo(Integer questaoCodigo) {
        this.questaoCodigo = questaoCodigo;
    }

    public String getQuestaoTexto() {
        return questaoTexto;
    }

    public void setQuestaoTexto(String questaoTexto) {
        this.questaoTexto = questaoTexto;
    }

    public Boolean getConcordoTotalmente() {
        return concordoTotalmente;
    }

    public void setConcordoTotalmente(Boolean concordoTotalmente) {
        this.concordoTotalmente = concordoTotalmente;
    }

    public Boolean getConcordo() {
        return concordo;
    }

    public void setConcordo(Boolean concordo) {
        this.concordo = concordo;
    }

    public Boolean getNaoConcordoNemDiscordo() {
        return naoConcordoNemDiscordo;
    }

    public void setNaoConcordoNemDiscordo(Boolean naoConcordoNemDiscordo) {
        this.naoConcordoNemDiscordo = naoConcordoNemDiscordo;
    }

    public Boolean getDiscordo() {
        return discordo;
    }

    public void setDiscordo(Boolean discordo) {
        this.discordo = discordo;
    }

    public Boolean getDiscordoTotalmente() {
        return discordoTotalmente;
    }

    public void setDiscordoTotalmente(Boolean discordoTotalmente) {
        this.discordoTotalmente = discordoTotalmente;
    }

    public Boolean getNaoSeiAvaliar() {
        return naoSeiAvaliar;
    }

    public void setNaoSeiAvaliar(Boolean naoSeiAvaliar) {
        this.naoSeiAvaliar = naoSeiAvaliar;
    }
}
