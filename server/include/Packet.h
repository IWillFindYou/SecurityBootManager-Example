#ifndef PACKET_H_
#define PACKET_H_

class Device;
class Packet
{
protected:
    Device *                            mDev;
    multimap< string, vector<Device> >* mGroups;
public:
    Packet() : mDev(NULL) {}
    virtual ~Packet() {}

    void          setDevice(Device* dev) { this->mDev = dev; }
    void          setGroups(multimap< string, vector<Device> >* groups) { this->mGroups = groups; }

    virtual int   parser(char* buff, int size) { return 0; }
    virtual char* encode(int* size) { return NULL; }
    virtual int   execute() { return 0; }
};

#endif

