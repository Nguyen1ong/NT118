package com.example.nt118_project.Model

class BusTicket {
    public var Name: String = ""
    public var NumberOfSeat: String = "1"
    public var DepartureTime: String = ""
    public var ArrivalTime: String = ""
    public var TravelTime: String = ""
    public var DeparturePoint: String = ""
    public var ArrivalPoint: String = ""
    public var Money: String = ""
    constructor(){}
    constructor(Name:String,
                NumberOfSeat:String,
                DepartureTime:String,
                ArrivalTime:String,
                TravelTime:String,
                DeparturePoint:String,
                ArrivalPoint:String,
        Money:String)
    {
        this.Name = Name
        this.NumberOfSeat = NumberOfSeat
        this.DepartureTime = DepartureTime
        this.ArrivalTime = ArrivalTime
        this.TravelTime = TravelTime
        this.DeparturePoint = DeparturePoint
        this.ArrivalPoint = ArrivalPoint
        this.Money = Money
    }
}