package com.example.nt118_project.Model

class ServiceCar_Ticket {
    public var id: String = ""
    public var type: String = ""
    public var Place: String = ""
    public var Name: String = ""
    public var Company: String = ""
    public var Status: String = ""
    public var NumSeat: Int = 0
    public var NumLuggage: Int = 0
    public var Price: Int = 0
    public var image: String = ""
    constructor(){}
    constructor(Company: String,
                Name: String,
                NumLuggage: Int,
                NumSeat: Int,
                Place :String,
                Price:Int,
                Status: String,
                id: String,
                image: String,
                type: String)
    {
        this.id = id
        this.type = type
        this.Place = Place
        this.Company = Company
        this.Name = Name
        this.Status = Status
        this.NumLuggage = NumLuggage
        this.NumSeat = NumSeat
        this.Price = Price
        this.image = image
    }

}