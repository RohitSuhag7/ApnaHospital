package com.example.apnahospital.di

import com.example.apnahospital.repository.AuthRepository
import com.example.apnahospital.repository.AuthRepositoryImp
import com.example.apnahospital.repository.PatientRepository
import com.example.apnahospital.repository.PatientRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseDatabaseInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthRepository =
        authRepositoryImp

    @Singleton
    @Provides
    fun providePatientRepository(patientRepositoryImp: PatientRepositoryImp): PatientRepository =
        patientRepositoryImp
}
