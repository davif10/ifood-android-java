package com.davisilvaprojetos.ifood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.davisilvaprojetos.ifood.R;
import com.davisilvaprojetos.ifood.helper.ConfiguracaoFirebase;
import com.davisilvaprojetos.ifood.helper.UsuarioFirebase;
import com.davisilvaprojetos.ifood.model.Empresa;
import com.davisilvaprojetos.ifood.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConfiguracoesUsuarioActivity extends AppCompatActivity {
    private EditText editUsuarioNome, editUsuarioEndereco;
    private String idUsuario;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_usuario);
        //Configurações iniciais
        inicializarComponentes();

        //Recuperar dados do usuário
        recuperarDadosUsuario();

    }

    private void recuperarDadosUsuario(){
        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null){
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    editUsuarioNome.setText(usuario.getNome());
                    editUsuarioEndereco.setText(usuario.getEndereco());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validarDadosUsuario(View view){
        //Valida se os campos foram preenchidos
        String nome = editUsuarioNome.getText().toString();
        String endereco = editUsuarioEndereco.getText().toString();

        if(!nome.isEmpty()){
            if(!endereco.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);
                usuario.setNome(nome);
                usuario.setEndereco(endereco);
                usuario.salvar();
                exibirMensagem("Dados atualizado com sucesso!");
                finish();

            }else{
                exibirMensagem("Digite seu endereço completo!");
            }
        }else{
            exibirMensagem("Digite seu nome!");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }


    private void inicializarComponentes(){
        //Configurações Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações usuário");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editUsuarioEndereco = findViewById(R.id.editUsuarioEndereco);
        editUsuarioNome = findViewById(R.id.editUsuarioNome);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idUsuario = UsuarioFirebase.getIdUsuario();
    }
}