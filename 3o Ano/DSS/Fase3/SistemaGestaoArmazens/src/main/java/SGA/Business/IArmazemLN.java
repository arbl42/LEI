package SGA.Business;

import SGA.dataaccess.*;
import exception.WrongCredentialsException;

import java.util.Map;
import java.util.UUID;

public class IArmazemLN {
    private final LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
    private final OrdemTransporteDAO ordemTransporteDAO = new OrdemTransporteDAO();
    private final RobotDAO robotDAO = new RobotDAO();
    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
    private final PaleteDAO paleteDAO = new PaleteDAO();
    private final CodigoQRDAO codigoQRDAO = new CodigoQRDAO();

    public Map<Integer, Localizacao> gerirPaletes() {
        return localizacaoDAO.getAllLocalizacoes();
    }

    public void iniciarSessaoGestor(int id, String password) throws WrongCredentialsException {
        Utilizador utilizador = (Utilizador) utilizadorDAO.get(id);

        if (utilizador == null || !utilizador.getTipo().equals("Gestor")) {
            throw new WrongCredentialsException();
        }

        boolean loggedIn = utilizador.validarCredenciais(id, password);

        if (!loggedIn) {
            throw new WrongCredentialsException();
        }
    }

    public void iniciarSessaoRobot(int id, String password) throws WrongCredentialsException {
        Robot robot = (Robot) robotDAO.get(id);

        if (robot == null) {
            throw new WrongCredentialsException();
        }

        boolean loggedIn = robot.validarCredenciais(id, password);

        if (!loggedIn) {
            throw new WrongCredentialsException();
        }
    }

    public void iniciarSessaoEncarregado(int id, String password) throws WrongCredentialsException {
        Utilizador utilizador = (Utilizador) utilizadorDAO.get(id);

        if (utilizador == null || !utilizador.getTipo().equals("Encarregado")) {
            throw new WrongCredentialsException();
        }

        boolean loggedIn = utilizador.validarCredenciais(id, password);

        if (!loggedIn) {
            throw new WrongCredentialsException();
        }
    }

    public Map<Integer, Robot> gerirRobots() {
        return robotDAO.getAllRobots();
    }

    public Map<Integer, OrdemTransporte> ordensTransportePendentes() {
        return ordemTransporteDAO.getOrdensTransportePendentes();
    }

    public void criarOrdemTransporte(String tipo, int seccao, int prateleira, int Gestor_id) {
        Localizacao localizacao = localizacaoDAO.getLocalizacao(seccao, prateleira);

        OrdemTransporte ordemTransporte = new OrdemTransporte(
                ordemTransporteDAO.getNextId(),
                "Pendente",
                tipo,
                localizacao.getId(),
                null,
                Gestor_id
        );

        ordemTransporteDAO.put(ordemTransporte.key(), ordemTransporte);
    }

    public void aceitarOrdTransp(int id, int Robot_id) {
        OrdemTransporte ordemTransporte = (OrdemTransporte) ordemTransporteDAO.get(id);
        ordemTransporte.setEstado("Aceite");
        ordemTransporte.setRobot_id(Robot_id);
        ordemTransporteDAO.update(id, ordemTransporte);
        if (ordemTransporte.getTipo().equals("Entrega")) {
            receberPalete(ordemTransporte);
        } else {
            enviarPalete(ordemTransporte);
        }
    }

    public void rejeitarOrdTransp(int id, int Robot_id) {
        OrdemTransporte ordemTransporte = (OrdemTransporte) ordemTransporteDAO.get(id);
        ordemTransporte.setEstado("Recusado");
        ordemTransporte.setRobot_id(Robot_id);
        ordemTransporteDAO.update(id, ordemTransporte);
    }

    public void receberPalete(OrdemTransporte ordemTransporte) {
        CodigoQR codigoQR = guardarQR();
        guardarPalete(ordemTransporte, codigoQR);
    }

    public CodigoQR guardarQR() {
        CodigoQR codigoQR = new CodigoQR(codigoQRDAO.getNextId(), UUID.randomUUID().toString());
        codigoQRDAO.put(codigoQR.key(), codigoQR);
        return codigoQR;
    }

    public void guardarPalete(OrdemTransporte ordemTransporte, CodigoQR codigoQR) {
        Palete palete = new Palete(paleteDAO.getNextId(), ordemTransporte.getLocalizacao_id(), codigoQR.key());
        paleteDAO.put(palete.key(), palete);
    }

    public void enviarPalete(OrdemTransporte ordemTransporte) {
        Localizacao localizacao = (Localizacao) localizacaoDAO.get(ordemTransporte.getLocalizacao_id());
        paleteDAO.deletePalete(localizacao.key());
    }

    public Map<Integer, OrdemTransporte> listOrdensTransporte() {
        return ordemTransporteDAO.getAllOrdensTransporte();
    }

    public Utilizador criarEncarregado(String nome, String password) {
        Utilizador encarregado = new Utilizador(utilizadorDAO.getNextId(), nome, password, "Encarregado");
        utilizadorDAO.put(encarregado.key(), encarregado);
        return encarregado;

    }

    public Utilizador criarRobot(String password) {
        Utilizador robot = new Utilizador(utilizadorDAO.getNextId(), "robot", password, "Robot");
        utilizadorDAO.put(robot.key(), robot);
        return robot;
    }
}
