package comparadorfiscal.application.pessoal;
import java.util.List;

public interface BuscarLinhasRgfPort {


    List<LinhaRgf> buscarPorMunicipio(Integer codIbge, Integer exercicio, Integer periodo);

}
