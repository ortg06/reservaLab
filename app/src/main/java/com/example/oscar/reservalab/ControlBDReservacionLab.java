package com.example.oscar.reservalab;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBDReservacionLab{

public class ControlBDReservacionLab {
    private static final String[] camposAsignatura=new String[]
            {"codigoAsignatura","nombreAsignatura"};
    private static final String[] camposProfesor=new String[]
            {"idProfesor","nombreProfesor","idUsuario","idAsignacionCarga"};
    private static final String[] camposAsignacionCarga=new String[]
            {"idAsignacionCarga","codigoAsignatura","idCiclo"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1="DROP TABLE IF EXISTS asignatura;";
    private static final String DROP_TABLE5="DROP TABLE IF EXISTS profesor;";
    private static final String DROP_TABLE12="DROP TABLE IF EXISTS asignacionCarga;";
    private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS ciclo; ";
    private static final String DROP_TABLE3 ="DROP TABLE IF EXISTS reservacion; ";


    private static final String[] camposCiclo=new String[] {"idCiclo","numCiclo", "anio"};
    private static final String[] camposAsignatura=new String[] {"codigoAsignatura","nombreAsignatura", "numCiclo"};
    private static final String[] camposReservacion=new String[] {"idReservacion","codLaboratorio",  "idProfesor"};


    public ControlBDReservacionLab(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BASE_DATOS = "reservacionLab.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {

            super(context, BASE_DATOS, null, VERSION);
        }
          @Override
        public void onCreate (SQLiteDatabase db){
            try{

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE profesor(idprofesor VARCHAR(10) NOT NULL PRIMARY KEY, nombreProfesor VARCHAR(30) NOT NULL,idUsuario VARCHAR(10) NOT NULL,idAsignacionCarga INTEGER NOT NULL);");
                db.execSQL("CREATE TABLE asignacionCarga(idAsignacionCarga INTEGER NOT NULL PRIMARY KEY, codigoAsignatura VARCHAR(10) NOT NULL, idCiclo INTEGER NOT NULL);");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        // public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
// }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE5);
                db.execSQL(DROP_TABLE12);
                onCreate(db);
            } catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
        public void abrir() throws SQLException{

            db = DBHelper.getWritableDatabase();
            return;
        }

        public void cerrar(){
            DBHelper.close();
        }

        ///INSERTAR ASIGNATURA
         public String insertar(Asignatura asignatura){

            String regInsertados="Registro Insertado Nº=";
            long contador=0;
             //Verificar que no exista asignatura
             if(verificarIntegridad(asignatura,1))
             {
                 regInsertados="Error al Insertar el registros, Registro Duplicado. Verificar inserción";

             }
             else
             {
                 ContentValues asig = new ContentValues();
                 asig.put("codigoAsignatura", asignatura.getCodigoAsignatura());
                 asig.put("nombreAsignatura", asignatura.getNombreAsignatura());
                 contador=db.insert("asignatura", null, asig);
                 regInsertados=regInsertados+contador;
             }
             return  regInsertados;

                db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL, idCiclo INTEGER NOT NULL);");
                db.execSQL("CREATE TABLE ciclo(idCiclo Integer NOT NULL PRIMARY KEY, numCiclo Integer NOT NULL, anio Integer NOT NULL);");
                db.execSQL("CREATE TABLE reservacion(idReservacion INTEGER NOT NULL PRIMARY KEY, codLaboratorio VARCHAR(10) NOT NULL, idProfesor VARCHAR(10) NOT NULL);");

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        @Override
        //  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


//        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }

    }

    public void abrir() throws SQLException{

        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }

    ///INSERTAR ASIGNATURA
    public String insertar(Asignatura asignatura){

        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues asig = new ContentValues();
        asig.put("codigoAsignatura", asignatura.getCodigoAsignatura());
        asig.put("nombreAsignatura", asignatura.getNombreAsignatura());
        asig.put("idCiclo", asignatura.getIdCiclo());

        contador=db.insert("asignatura", null, asig);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;

    }

     //INSERTAR CICLO
     public String insertar(Ciclo ciclo){

         String regInsertados="Registro Insertado Nº=";
         long contador=0;
         //Verificar que no exista ciclo
         if(verificarIntegridad(ciclo,2))
         {
             regInsertados="Error al Insertar el registros, Registro Duplicado. Verificar inserción";

         }
         else
         {
             ContentValues cicl = new ContentValues();
             cicl.put("idCiclo", ciclo.getIdCiclo());
             cicl.put("numCiclo", ciclo.getNumCiclo());
             cicl.put("anio", ciclo.getAnio());
             contador=db.insert("ciclo", null, cicl);
             regInsertados=regInsertados+contador;

         }
         return  regInsertados;
     }

            //INSERTAR ASIGNACION CARGA
        public String insertar(AsignacionCarga asignacionCarga) {

            String regInsertados = "Registro Insertado Nº=";
            long contador = 0;

            //verificar que no exista la AsignacionCarga
            if (verificarIntegridad(asignacionCarga, 1)) {
                if (verificarIntegridad(asignacionCarga, 2)) {
                    regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
                } else {
                    ContentValues asigna = new ContentValues();
                    asigna.put("idAsignacionCarga", asignacionCarga.getIdAsignacionCarga());
                    asigna.put("codigoAsignatura", asignacionCarga.getCodigoAsignatura());
                    asigna.put("idCiclo", asignacionCarga.getIdCiclo());
                    contador = db.insert("asignacionCarga", null, asigna);

                }
            } else {
                regInsertados = "Error al insertar el registro sin referencias.Verificar Insercion";

            }
            regInsertados = regInsertados + contador;
            return regInsertados;

        }

        //INSERTAR PROFESOR
        public String insertar(Profesor profesor) {

            String regInsertados = "Registro Insertado Nº=";
            long contador = 0;
            //Verificar que no exista profesor
            if (verificarIntegridad(profesor, 1)) {
                regInsertados = "Error al Insertar el registros, Registro Duplicado. Verificar inserción";

            } else {
                ContentValues prof = new ContentValues();
                prof.put("idProfesor", profesor.getIdProfesor());
                prof.put("nombreProfesor", profesor.getNombreProfesor());
                prof.put("idUsuario", profesor.getIdUsuario());
                prof.put("idAsignacionCarga", profesor.getIdAsignacionCarga());
                contador = db.insert("asignatura", null, prof);
                regInsertados = regInsertados + contador;
            }
            return regInsertados;
        }

            //ACTUALIZAR ASIGNATURA

    //Insertar Reservacion
    public String insertar(Reservacion reservacion){

        String regInsertados="Registro Nº= Insertado Correctamente ";
        long contador=0;

        if(verificarIntegridad(reservacion,5)) {  // 1 Verificar integridad referencial
                if(verificarIntegridad(reservacion,6))// 2 Verificar registro duplicado
                { regInsertados= "Error al Insertar el registro, Ya existe esta reservacion. Verificar"; }
                else { ContentValues reservaciones = new ContentValues();
                reservaciones.put("idReservacion", reservacion.getIdReservacion());
                reservaciones.put("codLaboratorio", reservacion.getCodLaboratorio());
                reservaciones.put("idProfesor", reservacion.getIdProfesor());


                contador=db.insert("reservacion", null, reservaciones); }
            }  else { regInsertados= "Error al Insertar el registro, Registro sin referencias. Verificar inserción"; }

        regInsertados=regInsertados+contador;
        return regInsertados;

    }


    //ACTUALIZAR ASIGNATURA

    public String actualizar(Asignatura asignatura){
        if(verificarIntegridad(asignatura,3)){
            String[] id={asignatura.getCodigoAsignatura()};
            ContentValues cv = new ContentValues();
            cv.put("codigoAsignatura", asignatura.getCodigoAsignatura());
            cv.put("nombreAsignatura", asignatura.getNombreAsignatura());
            cv.put("idCiclo", asignatura.getIdCiclo());
            db.update("asignatura", cv, "codigoAsignatura=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ asignatura.getCodigoAsignatura()+ "No existe";
        }
    }

    //ACTUALIZAR CICLO
    public String actualizar(Ciclo ciclo){
        if(verificarIntegridad(ciclo,2)){
            String[] id={Integer.toString(ciclo.getIdCiclo())};
            ContentValues cv = new ContentValues();
            cv.put("idCiclo", ciclo.getIdCiclo());
            cv.put("numCiclo", ciclo.getNumCiclo());
            cv.put("anio", ciclo.getAnio());

            db.update("ciclo", cv, "idCiclo=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ ciclo.getNumCiclo()+ "No existe";
        }
    }

//Actualizar reservacion
    public String actualizar(Reservacion reservacion){
        if(verificarIntegridad(reservacion,6)){
            String[] id={reservacion.getIdReservacion()};
            ContentValues cv = new ContentValues();
            cv.put("idReservacion", reservacion.getIdReservacion());
            cv.put("codLaboratorio", reservacion.getCodLaboratorio());
            cv.put("idProfesor", reservacion.getIdProfesor());
            db.update("reservacion", cv, "idReservacion=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ reservacion.getIdReservacion()+ "No existe";
        }
    }

    // Eliminar Asignatura
    public String eliminar(Asignatura asignatura){

        String regAfectados="filas afectadas= "; int contador=0;
        // 2 Verificar registro que exista
        if(verificarIntegridad(asignatura, 3)) { String where="codigoAsignatura='"+asignatura.getCodigoAsignatura()+"'";
        where=where+" AND idCiclo='"+asignatura.getIdCiclo()+"'";
        contador+=db.delete("asignatura", where, null);
        regAfectados+=contador;
        return regAfectados;
        } else { return "Registro no Existe"; }
    }

        //ACTUALIZAR ASIGNACION CARGA
        public String actualizar(AsignacionCarga asignacionCarga){
            if(verificarIntegridad(asignacionCarga,1)){
                String[] id={Integer.toString(asignacionCarga.getIdAsignacionCarga())};
                ContentValues cv = new ContentValues();
                cv.put("idAsignacionCarga", asignacionCarga.getIdAsignacionCarga());
                cv.put("codigoAsignatura", asignacionCarga.getCodigoAsignatura());
                cv.put("idCiclo", asignacionCarga.getIdCiclo());
                db.update("asignacionCarga", cv, "idAsignacionCarga =?", id);
                return "Registro Actualizado Correctamente";
            }else{
                return "Registro con Id de Asignacion de Carga"+ asignacionCarga.getIdAsignacionCarga()+ "No existe";
            }
        }
        //ACTUALIZAR PROFESOR
        public String actualizar(Profesor profesor){
            if(verificarIntegridad(profesor,1)){
                String[] id={profesor.getIdProfesor()};
                ContentValues cv = new ContentValues();
                cv.put("idProfesor", profesor.getIdProfesor());
                cv.put("nombreProfesor", profesor.getNombreProfesor());
                cv.put("idUsuario", profesor.getIdUsuario());
                cv.put("idAsignacionCarga", profesor.getIdAsignacionCarga());
                db.update("profesor", cv, "idProfesor=?", id);
                return "Registro Actualizado Correctamente";
            }else{
                return "Registro con código de Profesor"+ profesor.getIdProfesor()+ "No existe";
            }
        }

        // Eliminar Asignatura
        public String eliminar(Asignatura asignatura){
            String regAfectados="Filas afectadas=";
            int contador=0;

    // Eliminar Ciclo
    public String ciclo(Ciclo ciclo){
        String regAfectados="Filas afectadas=";
        int contador=0;
         //Si existe ciclo
        if(verificarIntegridad(ciclo,2)){
            //Si tiene materias relacionadas, se eliminan primero
            if (verificarIntegridad(ciclo,4)){
                contador+=db.delete("ciclo","idCiclo='"+ciclo.getIdCiclo()+"'",null);
            }
            contador+=db.delete("asignatura","codigoAsignatura='"+ciclo.getIdCiclo()+"'", null);
            regAfectados+=contador;
        }

        else{
            return "Registro con código de Asignatura" +ciclo.getIdCiclo()+ "no existe";

        }

        return regAfectados;
    }


    // Eliminar Reservacion
    public String eliminar(Reservacion reservacion){

        String regAfectados="filas afectadas= "; int contador=0;
        // 2 Verificar registro que exista
        if(verificarIntegridad(reservacion, 6)) { String where="idReservacion='"+reservacion.getIdReservacion()+"'";
            contador+=db.delete("reservacion", where, null);
            regAfectados+=contador;
            return regAfectados;
        } else { return "Registro no Existe"; }
    }
    //ELIMINAR ASIGNACION DE CARGA
    public String eliminar(AsignacionCarga asignacionCarga){
        String regAfectados="Filas afectadas=";
        int contador=0;

        if(verificarIntegridad(asignacionCarga,1)){
            if (verificarIntegridad(asignacionCarga,1)){
                contador+=db.delete("asignacionCarga","idAsignacionCarga='"+asignacionCarga.getIdAsignacionCarga()+"'",null);
            }
            contador+=db.delete("asignacionCarga","idAsignacionCarga='"+asignacionCarga.getIdAsignacionCarga()+"'", null);
            regAfectados+=contador;
        }

        else{
            return "Registro con Id de Asignacion" +asignacionCarga.getIdAsignacionCarga()+ "no existe";

        }

        return regAfectados;
    }


        //ELIMINAR PROFESOR
        public String eliminar(Profesor profesor){
            String regAfectados="Filas afectadas=";
            int contador=0;

            if(verificarIntegridad(profesor,1)){
                if (verificarIntegridad(profesor,1)){
                    contador+=db.delete("profesor","idProfesor='"+profesor.getIdProfesor()+"'",null);
                }
                contador+=db.delete("profesor","idProfesor='"+profesor.getIdProfesor()+"'", null);
                regAfectados+=contador;
            }

            else{
                return "Registro con Id de Profesor" +profesor.getIdProfesor()+ "no existe";

            }

            return regAfectados;
        }

    //CONSULTAR ASIGNATURA
        public Asignatura consultarAsignatura(String codigoAsignatura){

            String[] id={codigoAsignatura};
            Cursor cursor = db.query("asignatura",camposAsignatura,"codigoAsignatura=?",id,null,null,null);

            if(cursor.moveToFirst()){
                Asignatura asignatura = new Asignatura();
                asignatura.setCodigoAsignatura(cursor.getString(0));
                asignatura.setNombreAsignatura(cursor.getString(1));
                return asignatura;
            } else{
                return null;
            }

    public Asignatura consultarAsignatura(String codigoAsignatura, String nombreAsignatura, Integer idCiclo){
        String idciclo;
        idciclo=Integer.toString(idCiclo);
        String[] id={codigoAsignatura, nombreAsignatura, idciclo};
        Cursor cursor = db.query("asignatura",camposAsignatura,"idCiclo =? AND codigoAsignatura=?", id, null, null, null);


        if(cursor.moveToFirst()){
            Asignatura asignatura = new Asignatura();
            asignatura.setCodigoAsignatura(cursor.getString(0));
            asignatura.setNombreAsignatura(cursor.getString(1));
            asignatura.setIdCiclo(cursor.getInt(2));

            return asignatura;
        } else{
            return null;
        }
        //CONSULTAR ASIGNACION DE CARGA
        public AsignacionCarga consultarAsignacionCarga(int idAsignacionCarga){

            String[] id={Integer.toString(idAsignacionCarga)};
            Cursor cursor = db.query("asignacionCarga",camposAsignacionCarga,"idAsignacionCarga=?",id,null,null,null);

            if(cursor.moveToFirst()){
                AsignacionCarga asignacionCarga = new AsignacionCarga();
                asignacionCarga.setIdAsignacionCarga(cursor.getInt(0));
                asignacionCarga.setCodigoAsignatura(cursor.getString(1));
                asignacionCarga.setIdCiclo(cursor.getInt(3));
                return asignacionCarga;
            } else{
                return null;
            }

        }
        //CONSULTAR PROFESOR
        public Profesor consultarProfesor(String idProfesor){

            String[] id={idProfesor};
            Cursor cursor = db.query("profesor",camposProfesor,"idProfesor=?",id,null,null,null);

            if(cursor.moveToFirst()){
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(cursor.getString(0));
                profesor.setNombreProfesor(cursor.getString(1));
                profesor.setIdUsuario(cursor.getString(2));
                profesor.setIdAsignacionCarga(cursor.getInt(3));
                return profesor;
            } else{
                return null;
            }

        }

        private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
            switch (relacion){
                case 1: //Verifica la existencia de asignatura
                {  Asignatura asignatura2= (Asignatura)dato;
                   String[] id = {asignatura2.getCodigoAsignatura()};
                   abrir();
                   Cursor c2 = db.query("asignatura", null, "codigoAsignatura=?", id, null,null,null);

                   if(c2.moveToFirst()){
                       //Se encontro Asignatura
                       return  true;
                   }
                   return false;
                }
                case 7: {//VERIFICAR QUE AL INSERTAR ASIGNACION EXISTA LA MATERIA Y EL CICLO
                    AsignacionCarga asignacionCarga = (AsignacionCarga) dato;
                    String[] id1 = {asignacionCarga.getCodigoAsignatura()};
                    String[] id2 = {Integer.toString(asignacionCarga.getIdCiclo())};
                    abrir();
                    Cursor cursor1 = db.query("asignatura", null, "codigoAsignatura =?", id1, null, null, null);
                    Cursor cursor2 = db.query("ciclo", null, "idciclo =?", id2, null, null, null);
                    if (cursor2.moveToFirst() && cursor1.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 8:{
                    //verificar que al modificar la asignacion de carga existan la materia  y el ciclo
                    AsignacionCarga asignacionCarga1 = (AsignacionCarga)dato;
                    String[]  ids = {asignacionCarga1.getCodigoAsignatura(),Integer.toString(asignacionCarga1.getIdCiclo())};
                    abrir();
                    Cursor c = db.query("asignacionCarga",null, "codigoAsignatura = ? AND idCiclo =?",ids, null, null, null);
                    if(c.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 9: {
                    //Verificar que al insertar el profesor exista el id usuario, y el id de asignacion de carga
                    Profesor profesor = (Profesor) dato;
                    String[] id3 = {profesor.getIdUsuario()};
                    String[] id4 = {Integer.toString(profesor.getIdAsignacionCarga())};
                    abrir();
                    Cursor cursor3 = db.query("usuario", null, "idUsuario =?", id3, null, null, null);
                    Cursor cursor4 = db.query("asignacionCarga", null, "idAsignacionCarga =?", id4, null, null, null);
                    if (cursor4.moveToFirst() && cursor3.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 10:{
                    //Verificar que al modificar el maestro exista el usuario y la asignacion de carga
                    Profesor profesor1 = (Profesor) dato;
                    String[]  id5 = {profesor1.getIdUsuario(),Integer.toString(profesor1.getIdAsignacionCarga())};
                    abrir();
                    Cursor c = db.query("profesor",null, "idUsuario = ? AND idAsignacionCarga =?",id5, null, null, null);
                    if(c.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
            default:
            return false;
        }

    }

    //CONSULTAR CICLO
    public Ciclo consultarCiclo(Integer idCiclo){

        String idciclo;
        idciclo=Integer.toString(idCiclo);

        String[] id={idciclo};
        Cursor cursor = db.query("ciclo",camposCiclo,"idCiclo=?",id,null,null,null);


        if(cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setIdCiclo(cursor.getInt(0));
            ciclo.setNumCiclo(cursor.getInt(1));
            ciclo.setAnio(cursor.getInt(2));
            return ciclo;
        } else{
            return null;
        }

      public String llenarBDReservacionLab(){

            //variables para asignatura VA
            final String[] VAcodigo={"PRN115", "HDP115", "COS115", "MEP115","ANS115"};
            final String[] VAnombre={"Progamacion 1", "Herramientas de Producctividad", "Comunicaciones", "Metodos probabilisticos", "Analisis Numerico"};
            //variables para AsignacionCarga VAC
            final Integer[] VACidAsignacionCarga = {1,2,3,4};
            final String[] VACcodigoAsignatura = {"PRN115", "HDP115", "COS115", "MEP115"};
            final Integer[] VACidCiclo = {12016,22017,12018,22018};
            //Variables para Profesor VP
            final String[] VPidProfesor = {"prof1","prof2","prof3","prof4"};
            final String[] VPnombreProfesor = {"Carlos","Maria","Lucia","Jonathan"};
            final String[] VPidUsuario = {"user1","user2","user3","user4"};
            final Integer[] VPidAsignacionCarga = {1,2,3,4};
            abrir();
            db.execSQL("DELETE FROM asignatura;");
            db.execSQL("DELETE FROM asignacionCarga;");
            db.execSQL("DELETE FROM profesor;");
    }


    //CONSULTAR ASIGNATURA
    public Reservacion consultarReservacion(String idreservacion, String codLaboratorio, String idProfesor){

        String[] id={idreservacion,codLaboratorio, idProfesor};
        Cursor cursor = db.query("reservacion",camposReservacion,"codLaboratorio =? AND idProfesor=?", id, null, null, null);

        if(cursor.moveToFirst()){
            Reservacion reservacion = new Reservacion();
            reservacion.setIdReservacion(cursor.getString(0));
            reservacion.setCodLaboratorio(cursor.getString(1));
            reservacion.setIdProfesor(cursor.getString(2));
            return reservacion;
        } else{
            return null;
        }

    }

    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1: { // Verifica que al insertar Asignatura exista ciclo
                Asignatura asignatura = (Asignatura)dato;
                String[] id1 = {Integer.toString(asignatura.getIdCiclo())}; //Verifica que exista ciclo

                abrir();
                Cursor cursor1 = db.query("ciclo", null, "idCiclo = ?", id1, null, null, null);

                if(cursor1.moveToFirst()){ //Se encontraron datos ||
                    return true;
                } return false;
            }
            case 2:{
                //Verificar que exista ciclo
                Ciclo ciclo2 = (Ciclo)dato; String[] id = {Integer.toString(ciclo2.getNumCiclo())};
                abrir();
                Cursor c2 = db.query("ciclo", null, "idCiclo = ?", id, null, null, null);
                if(c2.moveToFirst()){ //Se encontro Ciclo
                    return true; }
            }

            case 3:{ //Verificar que exista asignatura
                Asignatura asignatura2 = (Asignatura)dato; String[] id = {asignatura2.getCodigoAsignatura()};
                abrir();
                Cursor c2 = db.query("asignatura", null, "codigoAsignatura = ?", id, null, null, null);
                if(c2.moveToFirst()){ //Se encontro Asignatura
                    return true; }
            }
            AsignacionCarga asignacionCarga = new AsignacionCarga();
            for (int i=0; i<4; i++){
                asignacionCarga.setIdAsignacionCarga(VACidAsignacionCarga[i]);
                asignacionCarga.setCodigoAsignatura(VACcodigoAsignatura[i]);
                asignacionCarga.setIdCiclo(VACidCiclo[i]);
                insertar(asignacionCarga);
            }
            Profesor profesor = new Profesor();
            for (int i=0; i<4; i++){
                profesor.setIdProfesor(VPidProfesor[i]);
                profesor.setNombreProfesor(VPnombreProfesor[i]);
                profesor.setIdUsuario(VPidUsuario[i]);
                profesor.setIdAsignacionCarga(VPidAsignacionCarga[i]);
                insertar(profesor);
            }

            case 4: { //Elimina las asignaturas
                Ciclo ciclo = (Ciclo)dato;
                Cursor c=db.query(true, "asignatura", new String[] { "codigoAsignatura" }, "idCiclo='"+ciclo.getIdCiclo()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else return false;
            }

            case 5: { // Verifica que al insertar una reservacion exista un laboratorio y profesor
                Reservacion reservacion = (Reservacion)dato;
                String[] id1 = {reservacion.getCodLaboratorio()}; //Verifica que exista laboratorio
                String[] id2 = {reservacion.getIdProfesor()}; //Verifica que exista profesor

                abrir();
                Cursor cursor1 = db.query("laboratorio", null, "codLaboratorio = ?", id1, null, null, null);
                Cursor cursor2 = db.query("profesor", null, "idProfesor = ?", id1, null, null, null);

                if(cursor1.moveToFirst() && cursor2.moveToFirst()){ //Se encontraron datos ||
                    return true;
                } return false;
            }
            case 6:{
                //Verificar que exista reservacion
                Reservacion reservacion2 = (Reservacion) dato;
                String[] id = {reservacion2.getIdReservacion()};
                abrir();
                Cursor c2 = db.query("reservacion", null, "idReservacion = ?", id, null, null, null);
                if(c2.moveToFirst()){ //Se encontro reservacion
                    return true; }
            }

            default:
                return false; }

    }

    public  String llenarBDReservacionLab(){

        //variables para asignatura VA
        final String[] VAcodigo={"PRN115", "HDP115", "COS115", "MEP115","ANS115"};
        final String[] VAnombre={"Progamacion 1", "Herramientas de Producctividad", "Comunicaciones", "Metodos probabilisticos", "Analisis Numerico"};
        final Integer[] VAnumCiclo={01,02};

        //Variables para Ciclo VC
        final Integer[] VCidCiclo={1,2,3,4};
        final Integer[] VCnumCico={01,02};
        final Integer[] VCanio={2017, 2018};

        abrir();
        db.execSQL("DELETE FROM asignatura;");
        db.execSQL("DELETE FROM ciclo;");


        Asignatura asignatura = new Asignatura();
        for(int i=0; i<2; i++){
            asignatura.setCodigoAsignatura(VAcodigo[i]);
            asignatura.setNombreAsignatura(VAnombre[i]);
            asignatura.setIdCiclo(VAnumCiclo[i]);
            insertar(asignatura);
        }

        Ciclo ciclo = new Ciclo();
        for(int i=0; i<2; i++){
            ciclo.setIdCiclo(VCidCiclo[i]);
            ciclo.setNumCiclo(VCnumCico[i]);
            ciclo.setAnio(VCanio[i]);
            insertar(ciclo);
        }

        cerrar();
        return "Guardado correctamente";
    }
}

