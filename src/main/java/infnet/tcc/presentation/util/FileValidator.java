/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infnet.tcc.presentation.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
import javax.servlet.http.Part;

/**
 *
 * @author peof
 */
@FacesValidator(value = "FileValidator")
public class FileValidator implements Validator {
    private static final int MAX_SIZE = 2 * 1024 * 1024;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;

        if (file.getSize() > MAX_SIZE) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Arquivo muito grande", "O arquivo deve ter o tamanho máximo de 2mb.");
            throw new ValidatorException(msg);
        }

        if (file.getContentType().contains("image/") == false) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Tipo de arquivo inválido", "O arquivo deve ser do tipo imagem.");
            throw new ValidatorException(msg);
        }
        

    }
}
